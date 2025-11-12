package org.apache.cassandra.transport;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.cassandra.transport.frame.FrameBodyTransformer;

public class Connection {

    static final AttributeKey<Connection> attributeKey = AttributeKey.valueOf("CONN");

    final private Channel channel;

    final private ProtocolVersion version;

    final private Tracker tracker;

    public Connection(Channel channel, ProtocolVersion version, Tracker tracker) {
        this.channel = channel;
        this.version = version;
        this.tracker = tracker;
        tracker.addConnection(channel, this);
    }

    public Tracker getTracker() {
        return tracker;
    }

    public ProtocolVersion getVersion() {
        return version;
    }

    public Channel channel() {
        return channel;
    }

    public interface Factory {

        Connection newConnection(Channel channel, ProtocolVersion version);
    }

    public interface Tracker {

        void addConnection(Channel ch, Connection connection);
    }

    public void setThrowOnOverload(boolean throwOnOverload) {
        this.throwOnOverload = throwOnOverload;
    }

    public void setTransformer(FrameBodyTransformer transformer) {
        this.transformer = transformer;
    }

    private boolean throwOnOverload;

    private volatile FrameBodyTransformer transformer;

    public boolean isThrowOnOverload() {
        return throwOnOverload;
    }

    public FrameBodyTransformer getTransformer() {
        return transformer;
    }
}
