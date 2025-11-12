package org.apache.cassandra.transport;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.exceptions.InvalidRequestException;
import org.apache.cassandra.transport.messages.ErrorMessage;
import io.netty.util.Attribute;

public class Frame {

    public static final byte PROTOCOL_VERSION_MASK = 0x7f;

    public final Header header;

    public final ByteBuf body;

    private Frame(Header header, ByteBuf body) {
        this.header = header;
        this.body = body;
    }

    public void retain() {
        body.retain();
    }

    public boolean release() {
        return body.release();
    }

    public static Frame create(Message.Type type, int streamId, ProtocolVersion version, EnumSet<Header.Flag> flags, ByteBuf body) {
        Header header = new Header(version, flags, streamId, type, body.readableBytes());
        return new Frame(header, body);
    }

    public static class Header {

        public static final int LENGTH = 9;

        public static final int BODY_LENGTH_SIZE = 4;

        public final ProtocolVersion version;

        public final EnumSet<Flag> flags;

        public final int streamId;

        public final Message.Type type;

        



        private Header(ProtocolVersion version, EnumSet<Flag> flags, int streamId, Message.Type type, long bodySizeInBytes) {
            this.version = version;
            this.flags = flags;
            this.streamId = streamId;
            this.type = type;
            this.bodySizeInBytes = bodySizeInBytes;
        }

        public enum Flag {

            COMPRESSED, TRACING, CUSTOM_PAYLOAD, WARNING, USE_BETA;

            static final private Flag[] ALL_VALUES = values();

            public static EnumSet<Flag> deserialize(int flags) {
                EnumSet<Flag> set = EnumSet.noneOf(Flag.class);
                for (int n = 0; n < ALL_VALUES.length; n++) {
                    if ((flags & (1 << n)) != 0)
                        set.add(ALL_VALUES[n]);
                }
                return set;
            }

            public static int serialize(EnumSet<Flag> flags) {
                int i = 0;
                for (Flag flag : flags) i |= 1 << flag.ordinal();
                return i;
            }
        }

        public final long bodySizeInBytes;
    }

    public Frame with(ByteBuf newBody) {
        return new Frame(header, newBody);
    }

    public static class Decoder extends ByteToMessageDecoder {

        static final private int MAX_FRAME_LENGTH = DatabaseDescriptor.getNativeTransportMaxFrameSize();

        private boolean discardingTooLongFrame;

        private long tooLongFrameLength;

        private long bytesToDiscard;

        private int tooLongStreamId;

        final private Connection.Factory factory;

        public Decoder(Connection.Factory factory) {
            this.factory = factory;
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> results) throws Exception {
            if (discardingTooLongFrame) {
                bytesToDiscard = discard(buffer, bytesToDiscard);
                if (bytesToDiscard <= 0)
                    fail();
                return;
            }
            int readableBytes = buffer.readableBytes();
            if (readableBytes == 0)
                return;
            int idx = buffer.readerIndex();
            int firstByte = buffer.getByte(idx++);
            Message.Direction direction = Message.Direction.extractFromVersion(firstByte);
            int versionNum = firstByte & PROTOCOL_VERSION_MASK;
            ProtocolVersion version = ProtocolVersion.decode(versionNum);
            if (readableBytes < Header.LENGTH)
                return;
            int flags = buffer.getByte(idx++);
            EnumSet<Header.Flag> decodedFlags = Header.Flag.deserialize(flags);
            if (version.isBeta() && !decodedFlags.contains(Header.Flag.USE_BETA))
                throw new ProtocolException(String.format("Beta version of the protocol used (%s), but USE_BETA flag is unset", version), version);
            int streamId = buffer.getShort(idx);
            idx += 2;
            Message.Type type;
            try {
                type = Message.Type.fromOpcode(buffer.getByte(idx++), direction);
            } catch (ProtocolException e) {
                throw ErrorMessage.wrap(e, streamId);
            }
            long bodyLength = buffer.getUnsignedInt(idx);
            idx += Header.BODY_LENGTH_SIZE;
            long frameLength = bodyLength + Header.LENGTH;
            if (frameLength > MAX_FRAME_LENGTH) {
                discardingTooLongFrame = true;
                tooLongStreamId = streamId;
                tooLongFrameLength = frameLength;
                bytesToDiscard = discard(buffer, frameLength);
                if (bytesToDiscard <= 0)
                    fail();
                return;
            }
            if (buffer.readableBytes() < frameLength)
                return;
            ByteBuf body = buffer.slice(idx, (int) bodyLength);
            body.retain();
            idx += bodyLength;
            buffer.readerIndex(idx);
            Attribute<Connection> attrConn = ctx.channel().attr(Connection.attributeKey);
            Connection connection = attrConn.get();
            if (connection == null) {
                connection = factory.newConnection(ctx.channel(), version);
                attrConn.set(connection);
            } else if (connection.getVersion() != version) {
                throw ErrorMessage.wrap(new ProtocolException(String.format("Invalid message version. Got %s but previous messages on this connection had version %s", version, connection.getVersion())), streamId);
            }
            
results.add(new Frame(new Header(version, decodedFlags, streamId, type), body));

        }

        private void fail() {
            long tooLongFrameLength = this.tooLongFrameLength;
            this.tooLongFrameLength = 0;
            discardingTooLongFrame = false;
            String msg = String.format("Request is too big: length %d exceeds maximum allowed length %d.", tooLongFrameLength, MAX_FRAME_LENGTH);
            throw ErrorMessage.wrap(new InvalidRequestException(msg), tooLongStreamId);
        }
    }

    static private long discard(ByteBuf buffer, long remainingToDiscard) {
        int availableToDiscard = (int) Math.min(remainingToDiscard, buffer.readableBytes());
        buffer.skipBytes(availableToDiscard);
        return remainingToDiscard - availableToDiscard;
    }

    @ChannelHandler.Sharable
    public static class Encoder extends MessageToMessageEncoder<Frame> {

        public void encode(ChannelHandlerContext ctx, Frame frame, List<Object> results) throws IOException {
            ByteBuf header = CBUtil.allocator.buffer(Header.LENGTH);
            Message.Type type = frame.header.type;
            header.writeByte(type.direction.addToVersion(frame.header.version.asInt()));
            header.writeByte(Header.Flag.serialize(frame.header.flags));
            if (frame.header.version.isGreaterOrEqualTo(ProtocolVersion.V3))
                header.writeShort(frame.header.streamId);
            else
                header.writeByte(frame.header.streamId);
            header.writeByte(type.opcode);
            header.writeInt(frame.body.readableBytes());
            results.add(header);
            results.add(frame.body);
        }
    }

    @ChannelHandler.Sharable
    public static class Decompressor extends MessageToMessageDecoder<Frame> {

        public void decode(ChannelHandlerContext ctx, Frame frame, List<Object> results) throws IOException {
            Connection connection = ctx.channel().attr(Connection.attributeKey).get();
            if (!frame.header.flags.contains(Header.Flag.COMPRESSED) || connection == null) {
                results.add(frame);
                return;
            }
            FrameCompressor compressor = connection.getCompressor();
            if (compressor == null) {
                results.add(frame);
                return;
            }
            results.add(compressor.decompress(frame));
        }
    }

    @ChannelHandler.Sharable
    public static class Compressor extends MessageToMessageEncoder<Frame> {

        public void encode(ChannelHandlerContext ctx, Frame frame, List<Object> results) throws IOException {
            Connection connection = ctx.channel().attr(Connection.attributeKey).get();
            if (frame.header.type == Message.Type.STARTUP || connection == null) {
                results.add(frame);
                return;
            }
            FrameCompressor compressor = connection.getCompressor();
            if (compressor == null) {
                results.add(frame);
                return;
            }
            frame.header.flags.add(Header.Flag.COMPRESSED);
            results.add(compressor.compress(frame));
        }
    }
}
