package backtype.storm.messaging.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import java.io.IOException;

public class SaslMessageToken implements INettySerializable {

    private byte[] token;

    public SaslMessageToken() {
    }

    public SaslMessageToken(byte[] token) {
        this.token = token;
    }

    public byte[] getSaslToken() {
        return token;
    }

    public void setSaslToken(byte[] token) {
        this.token = token;
    }

    public int encodeLength() {
        return 2 + 4 + token.length;
    }

    public ChannelBuffer buffer() throws IOException {
        ChannelBufferOutputStream bout = new ChannelBufferOutputStream(ChannelBuffers.directBuffer(encodeLength()));
        int payload_len = 0;
        if (token != null)
            payload_len = token.length;
        
<<<<<<< commits-hd_100/apache/storm/62d725a85e7869290805cdbe55f1f3bce1f905de-d0d3ca114014d6030f4b7fe6592d8de9a9e164e6/A.java
bout.writeShort(IDENTIFIER);
=======
bout.writeShort(identifier);
>>>>>>> commits-hd_100/apache/storm/62d725a85e7869290805cdbe55f1f3bce1f905de-d0d3ca114014d6030f4b7fe6592d8de9a9e164e6/B.java

        bout.writeInt(payload_len);
        if (payload_len > 0) {
            bout.write(token);
        }
        bout.close();
        return bout.buffer();
    }

    static final public short IDENTIFIER = -500;

    static public SaslMessageToken read(byte[] serial) {
        ChannelBuffer sm_buffer = ChannelBuffers.copiedBuffer(serial);
        short identifier = sm_buffer.readShort();
        int payload_len = sm_buffer.readInt();
        if (identifier != -500) {
            return null;
        }
        byte[] token = new byte[payload_len];
        sm_buffer.readBytes(token, 0, payload_len);
        return new SaslMessageToken(token);
    }
}
