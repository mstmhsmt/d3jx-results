package org.apache.cassandra.transport;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.cassandra.transport.frame.FrameBodyTransformer;

public class Connection {
  static final AttributeKey<Connection> attributeKey = AttributeKey.valueOf("CONN");

  private final Channel channel;

  private final ProtocolVersion version;

  private final Tracker tracker;

  private volatile FrameBodyTransformer transformer;

  public Connection(Channel channel, ProtocolVersion version, Tracker tracker) {
    this.channel = channel;
    this.version = version;
    this.tracker = tracker;
    tracker.addConnection(channel, this);
  }

  public void setTransformer(FrameBodyTransformer transformer) {
    this.transformer = transformer;
  }

  public FrameBodyTransformer getTransformer() {
    return transformer;
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

  private boolean throwOnOverload;

  public void setThrowOnOverload(boolean throwOnOverload) {
    this.throwOnOverload = throwOnOverload;
  }

  public boolean isThrowOnOverload() {
    return throwOnOverload;
  }
}