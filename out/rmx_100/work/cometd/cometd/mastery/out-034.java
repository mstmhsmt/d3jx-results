package org.cometd.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.cometd.bayeux.Channel;
import org.cometd.bayeux.ChannelId;
import org.cometd.bayeux.MarkedReference;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSession;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractClientSession implements ClientSession, Dumpable {

    static final private Logger logger = LoggerFactory.getLogger(ClientSession.class);

    static final private AtomicLong _idGen = new AtomicLong(0);

    final private List<Extension> _extensions = new CopyOnWriteArrayList<>();

    final private AttributesMap _attributes = new AttributesMap();

    final private ConcurrentMap<String, AbstractSessionChannel> _channels = new ConcurrentHashMap<>();

    final private Map<String, ClientSessionChannel.MessageListener> _callbacks = new ConcurrentHashMap<>();

    final private Map<String, ClientSessionChannel.MessageListener> _subscribers = new ConcurrentHashMap<>();

    final private AtomicInteger _batch = new AtomicInteger();

    protected AbstractClientSession() {
    }

    protected String newMessageId() {
        return String.valueOf(_idGen.incrementAndGet());
    }

    @Override
    public void addExtension(Extension extension) {
        _extensions.add(extension);
    }

    @Override
    public void removeExtension(Extension extension) {
        _extensions.remove(extension);
    }

    @Override
    public List<Extension> getExtensions() {
        return Collections.unmodifiableList(_extensions);
    }

    protected boolean extendSend(Message.Mutable message) {
        if (message.isMeta()) {
            for (Extension extension : _extensions) {
                if (!extension.sendMeta(this, message)) {
                    return false;
                }
            }
        } else {
            for (Extension extension : _extensions) {
                if (!extension.send(this, message)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean extendRcv(Message.Mutable message) {
        if (message.isMeta()) {
            for (Extension extension : _extensions) {
                if (!extension.rcvMeta(this, message)) {
                    return false;
                }
            }
        } else {
            for (Extension extension : _extensions) {
                if (!extension.rcv(this, message)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected abstract ChannelId newChannelId(String channelId);

    protected abstract AbstractSessionChannel newChannel(ChannelId channelId);

    @Override
    public ClientSessionChannel getChannel(String channelName) {
        return getChannel(channelName, null);
    }

    public ClientSessionChannel getChannel(ChannelId channelId) {
        return getChannel(channelId.toString(), channelId);
    }

    private ClientSessionChannel getChannel(String channelName, ChannelId channelId) {
        AbstractSessionChannel channel = _channels.get(channelName);
        if (channel == null) {
            if (channelId == null) {
                channelId = newChannelId(channelName);
            }
            AbstractSessionChannel newChannel = newChannel(channelId);
            channel = _channels.putIfAbsent(channelName, newChannel);
            if (channel == null) {
                channel = newChannel;
            }
        }
        return channel;
    }

    protected ConcurrentMap<String, AbstractSessionChannel> getChannels() {
        return _channels;
    }

    @Override
    public void startBatch() {
        _batch.incrementAndGet();
    }

    protected abstract void sendBatch();

    @Override
    public boolean endBatch() {
        if (_batch.decrementAndGet() == 0) {
            sendBatch();
            return true;
        }
        return false;
    }

    @Override
    public void batch(Runnable batch) {
        startBatch();
        try {
            batch.run();
        } finally {
            endBatch();
        }
    }

    protected boolean isBatching() {
        return _batch.get() > 0;
    }

    @Override
    public Object getAttribute(String name) {
        return _attributes.getAttribute(name);
    }

    @Override
    public Set<String> getAttributeNames() {
        return _attributes.getAttributeNameSet();
    }

    @Override
    public Object removeAttribute(String name) {
        Object old = _attributes.getAttribute(name);
        _attributes.removeAttribute(name);
        return old;
    }

    @Override
    public void setAttribute(String name, Object value) {
        _attributes.setAttribute(name, value);
    }

    protected void resetSubscriptions() {
        for (AbstractSessionChannel ch : _channels.values()) {
            ch.resetSubscriptions();
        }
    }

    public void receive(final Message.Mutable message) {
        String channelName = message.getChannel();
        if (channelName == null) {
            throw new IllegalArgumentException("Bayeux messages must have a channel, " + message);
        }
        if (Channel.META_SUBSCRIBE.equals(channelName)) {
            ClientSessionChannel.MessageListener subscriber = unregisterSubscriber(message.getId());
            if (!message.isSuccessful()) {
                String subscription = (String) message.get(Message.SUBSCRIPTION_FIELD);
                MarkedReference<AbstractSessionChannel> channelRef = getReleasableChannel(subscription);
                AbstractSessionChannel channel = channelRef.getReference();
                channel.removeSubscription(subscriber);
                if (channelRef.isMarked()) {
                    channel.release();
                }
            }
        }
        if (!extendRcv(message)) {
            return;
        }
        if (handleRemoteCall(message))
            return;
        notifyListeners(message);
    }

    protected void notifyListeners(Message.Mutable message) {
        if (message.isMeta() || message.isPublishReply()) {
            String messageId = message.getId();
            ClientSessionChannel.MessageListener callback = unregisterCallback(messageId);
            if (callback != null)
                notifyListener(callback, message);
        }
        MarkedReference<AbstractSessionChannel> channelRef = getReleasableChannel(message.getChannel());
        AbstractSessionChannel channel = channelRef.getReference();
        channel.notifyMessageListeners(message);
        if (channelRef.isMarked()) {
            channel.release();
        }
        ChannelId channelId = channel.getChannelId();
        for (String wildChannelName : channelId.getWilds()) {
            MarkedReference<AbstractSessionChannel> wildChannelRef = getReleasableChannel(wildChannelName);
            AbstractSessionChannel wildChannel = wildChannelRef.getReference();
            wildChannel.notifyMessageListeners(message);
            if (wildChannelRef.isMarked()) {
                wildChannel.release();
            }
        }
    }

    protected void notifyListener(ClientSessionChannel.MessageListener listener, Message.Mutable message) {
        MarkedReference<AbstractSessionChannel> channelRef = getReleasableChannel(message.getChannel());
        AbstractSessionChannel channel = channelRef.getReference();
        channel.notifyOnMessage(listener, message);
        if (channelRef.isMarked()) {
            channel.release();
        }
    }

    private MarkedReference<AbstractSessionChannel> getReleasableChannel(String id) {
        AbstractSessionChannel channel = ChannelId.isMeta(id) ? (AbstractSessionChannel) getChannel(id) : getChannels().get(id);
        if (channel != null) {
            return new MarkedReference<>(channel, false);
        }
        return new MarkedReference<>(newChannel(newChannelId(id)), true);
    }

    protected void registerCallback(String messageId, ClientSessionChannel.MessageListener callback) {
        if (callback != null) {
            _callbacks.put(messageId, callback);
        }
    }

    protected ClientSessionChannel.MessageListener unregisterCallback(String messageId) {
        if (messageId == null)
            return null;
        return _callbacks.remove(messageId);
    }

    protected void registerSubscriber(String messageId, ClientSessionChannel.MessageListener subscriber) {
        if (subscriber != null) {
            _subscribers.put(messageId, subscriber);
        }
    }

    protected ClientSessionChannel.MessageListener unregisterSubscriber(String messageId) {
        if (messageId == null)
            return null;
        return _subscribers.remove(messageId);
    }

    @Override
    public String dump() {
        return ContainerLifeCycle.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        ContainerLifeCycle.dumpObject(out, this);
        List<Dumpable> children = new ArrayList<>();
        children.add(new Dumpable() {

            @Override
            public String dump() {
                return null;
            }

            @Override
            public void dump(Appendable out, String indent) throws IOException {
                Collection<AbstractSessionChannel> channels = getChannels().values();
                ContainerLifeCycle.dumpObject(out, "channels: " + channels.size());
                ContainerLifeCycle.dump(out, indent, channels);
            }
        });
        ContainerLifeCycle.dump(out, indent, children);
    }

    protected abstract class AbstractSessionChannel implements Dumpable, ClientSessionChannel {

        final private ChannelId _id;

        final private AttributesMap _attributes = new AttributesMap();

        final private CopyOnWriteArrayList<MessageListener> _subscriptions = new CopyOnWriteArrayList<>();

        final private AtomicInteger _subscriptionCount = new AtomicInteger();

        final private CopyOnWriteArrayList<ClientSessionChannelListener> _listeners = new CopyOnWriteArrayList<>();

        private volatile boolean _released;

        protected AbstractSessionChannel(ChannelId id) {
            _id = id;
        }

        @Override
        public ChannelId getChannelId() {
            return _id;
        }

        @Override
        public void addListener(ClientSessionChannelListener listener) {
            throwIfReleased();
            _listeners.add(listener);
        }

        @Override
        public void removeListener(ClientSessionChannelListener listener) {
            throwIfReleased();
            _listeners.remove(listener);
        }

        @Override
        public List<ClientSessionChannelListener> getListeners() {
            return Collections.unmodifiableList(_listeners);
        }

        @Override
        public void publish(Object data) {
            publish(data, null);
        }

        @Override
        public void subscribe(MessageListener listener) {
            subscribe(listener, null);
        }

        @Override
        public void subscribe(MessageListener listener, MessageListener callback) {
            throwIfReleased();
            boolean added = _subscriptions.add(listener);
            if (added) {
                int count = _subscriptionCount.incrementAndGet();
                if (count == 1) {
                    sendSubscribe(listener, callback);
                }
            }
        }

        protected void sendSubscribe(MessageListener listener, MessageListener callback) {
            Message.Mutable message = newMessage();
            String messageId = newMessageId();
            message.setId(messageId);
            message.setChannel(Channel.META_SUBSCRIBE);
            message.put(Message.SUBSCRIPTION_FIELD, getId());
            registerSubscriber(messageId, listener);
            registerCallback(messageId, callback);
            send(message);
        }

        @Override
        public void unsubscribe(MessageListener listener) {
            unsubscribe(listener, null);
        }

        @Override
        public void unsubscribe(MessageListener listener, MessageListener callback) {
            boolean removedLast = removeSubscription(listener);
            if (removedLast) {
                sendUnSubscribe(callback);
            }
        }

        private boolean removeSubscription(MessageListener listener) {
            throwIfReleased();
            boolean removed = _subscriptions.remove(listener);
            if (removed) {
                return _subscriptionCount.decrementAndGet() == 0;
            }
            return false;
        }

        protected void sendUnSubscribe(MessageListener callback) {
            Message.Mutable message = newMessage();
            String messageId = newMessageId();
            message.setId(messageId);
            message.setChannel(Channel.META_UNSUBSCRIBE);
            message.put(Message.SUBSCRIPTION_FIELD, getId());
            registerCallback(messageId, callback);
            send(message);
        }

        @Override
        public void unsubscribe() {
            throwIfReleased();
            for (MessageListener listener : _subscriptions) {
                unsubscribe(listener);
            }
        }

        @Override
        public List<MessageListener> getSubscribers() {
            return Collections.unmodifiableList(_subscriptions);
        }

        @Override
        public boolean release() {
            if (_released) {
                return false;
            }
            if (_subscriptions.isEmpty() && _listeners.isEmpty()) {
                boolean removed = _channels.remove(getId(), this);
                _released = removed;
                return removed;
            }
            return false;
        }

        @Override
        public boolean isReleased() {
            return _released;
        }

        protected void resetSubscriptions() {
            throwIfReleased();
            for (MessageListener l : _subscriptions) {
                if (_subscriptions.remove(l)) {
                    _subscriptionCount.decrementAndGet();
                }
            }
        }

        @Override
        public String getId() {
            return _id.toString();
        }

        @Override
        public boolean isDeepWild() {
            return _id.isDeepWild();
        }

        @Override
        public boolean isMeta() {
            return _id.isMeta();
        }

        @Override
        public boolean isService() {
            return _id.isService();
        }

        @Override
        public boolean isBroadcast() {
            return !isMeta() && !isService();
        }

        @Override
        public boolean isWild() {
            return _id.isWild();
        }

        protected void notifyMessageListeners(Message message) {
            throwIfReleased();
            for (ClientSessionChannelListener listener : _listeners) {
                if (listener instanceof ClientSessionChannel.MessageListener) {
                    notifyOnMessage((MessageListener) listener, message);
                }
            }
            for (ClientSessionChannelListener listener : _subscriptions) {
                if (listener instanceof ClientSessionChannel.MessageListener) {
                    if (!message.isPublishReply()) {
                        notifyOnMessage((MessageListener) listener, message);
                    }
                }
            }
        }

        protected void notifyOnMessage(MessageListener listener, Message message) {
            throwIfReleased();
            try {
                listener.onMessage(this, message);
            } catch (Throwable x) {
                logger.info("Exception while invoking listener " + listener, x);
            }
        }

        @Override
        public void setAttribute(String name, Object value) {
            throwIfReleased();
            _attributes.setAttribute(name, value);
        }

        @Override
        public Object getAttribute(String name) {
            throwIfReleased();
            return _attributes.getAttribute(name);
        }

        @Override
        public Set<String> getAttributeNames() {
            throwIfReleased();
            return _attributes.getAttributeNameSet();
        }

        @Override
        public Object removeAttribute(String name) {
            throwIfReleased();
            Object old = getAttribute(name);
            _attributes.removeAttribute(name);
            return old;
        }

        protected void throwIfReleased() {
            if (isReleased()) {
                throw new IllegalStateException("Channel " + this + " has been released");
            }
        }

        @Override
        public String dump() {
            return ContainerLifeCycle.dump(this);
        }

        @Override
        public void dump(Appendable out, String indent) throws IOException {
            ContainerLifeCycle.dumpObject(out, this);
            List<Dumpable> children = new ArrayList<>();
            children.add(new Dumpable() {

                @Override
                public String dump() {
                    return null;
                }

                @Override
                public void dump(Appendable out, String indent) throws IOException {
                    List<ClientSessionChannelListener> listeners = getListeners();
                    ContainerLifeCycle.dumpObject(out, "listeners: " + listeners.size());
                    ContainerLifeCycle.dump(out, indent, listeners);
                }
            });
            children.add(new Dumpable() {

                @Override
                public String dump() {
                    return null;
                }

                @Override
                public void dump(Appendable out, String indent) throws IOException {
                    List<MessageListener> subscribers = getSubscribers();
                    ContainerLifeCycle.dumpObject(out, "subscribers: " + subscribers.size());
                    ContainerLifeCycle.dump(out, indent, subscribers);
                }
            });
            ContainerLifeCycle.dump(out, indent, children);
        }

        @Override
        public String toString() {
            return String.format("%s@%s", _id, AbstractClientSession.this);
        }

        @Override
        public void publish(Object data, MessageListener callback) {
            throwIfReleased();
            Message.Mutable message = newMessage();
            String messageId = newMessageId();
            message.setId(messageId);
            message.setChannel(getId());
            message.setData(data);
            registerCallback(messageId, callback);
            send(message);
        }
    }

    @Override
    public void remoteCall(String target, Object data, MessageListener callback) {
        if (!target.startsWith("/"))
            target = "/" + target;
        String channelName = "/service" + target;
        Message.Mutable message = newMessage();
        String messageId = newMessageId();
        message.setId(messageId);
        message.setChannel(channelName);
        message.setData(data);
        _remoteCalls.put(messageId, callback);
        send(message);
    }

    protected Message.Mutable newMessage() {
        return new HashMapMessage();
    }

    protected abstract void send(Message.Mutable message);

    private void notifyMessageListener(MessageListener listener, Message.Mutable message) {
        try {
            listener.onMessage(message);
        } catch (Throwable x) {
            logger.info("Exception while invoking listener " + listener, x);
        }
    }

    final private Map<String, MessageListener> _remoteCalls = new ConcurrentHashMap<>();

    private boolean handleRemoteCall(Message.Mutable message) {
        String messageId = message.getId();
        if (messageId != null) {
            MessageListener listener = _remoteCalls.remove(messageId);
            if (listener != null) {
                notifyMessageListener(listener, message);
                return true;
            }
        }
        return false;
    }
}
