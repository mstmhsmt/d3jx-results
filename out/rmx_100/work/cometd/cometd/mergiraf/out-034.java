/*
 * Copyright (c) 2008-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * <p>Partial implementation of {@link ClientSession}.</p>
 * <p>It handles extensions and batching, and provides utility methods to be used by subclasses.</p>
 */
<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
public abstract class AbstractClientSession implements ClientSession, Dumpable
{
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
public abstract class AbstractClientSession implements ClientSession, Dumpable
{
    protected static final String SUBSCRIBER_KEY = "org.cometd.client.subscriber";
    protected static final String CALLBACK_KEY = "org.cometd.client.callback";
=======
public abstract class AbstractClientSession implements ClientSession, Dumpable {
    protected static final String SUBSCRIBER_KEY = "org.cometd.client.subscriber";
    protected static final String CALLBACK_KEY = "org.cometd.client.callback";
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
    private static final Logger logger = LoggerFactory.getLogger(ClientSession.class);
    private static final AtomicLong _idGen = new AtomicLong(0);

    private final List<Extension> _extensions = new CopyOnWriteArrayList<>();
    private final AttributesMap _attributes = new AttributesMap();
    private final ConcurrentMap<String, AbstractSessionChannel> _channels = new ConcurrentHashMap<>();
    private final Map<String, ClientSessionChannel.MessageListener> _callbacks = new ConcurrentHashMap<>();
    private final Map<String, ClientSessionChannel.MessageListener> _subscribers = new ConcurrentHashMap<>();
    private final Map<String, MessageListener> _remoteCalls = new ConcurrentHashMap<>();
    private final AtomicInteger _batch = new AtomicInteger();

    protected AbstractClientSession() {
    }

    protected String newMessageId() {
        return String.valueOf(_idGen.incrementAndGet());
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public void addExtension(Extension extension)
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public void addExtension(Extension extension)
    {
=======
    public void addExtension(Extension extension) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        _extensions.add(extension);
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public void removeExtension(Extension extension)
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public void removeExtension(Extension extension)
    {
=======
    public void removeExtension(Extension extension) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        _extensions.remove(extension);
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public List<Extension> getExtensions()
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public List<Extension> getExtensions()
    {
=======
    public List<Extension> getExtensions() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
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

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public ClientSessionChannel getChannel(String channelName)
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public ClientSessionChannel getChannel(String channelName)
    {
=======
    public ClientSessionChannel getChannel(String channelName) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
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

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public void startBatch()
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public void startBatch()
    {
=======
    public void startBatch() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        _batch.incrementAndGet();
    }

    protected abstract void sendBatch();

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public boolean endBatch()
    {
        if (_batch.decrementAndGet() == 0)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public boolean endBatch()
    {
        if (_batch.decrementAndGet() == 0)
        {
=======
    public boolean endBatch() {
        if (_batch.decrementAndGet() == 0) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            sendBatch();
            return true;
        }
        return false;
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public void batch(Runnable batch)
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public void batch(Runnable batch)
    {
=======
    public void batch(Runnable batch) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
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

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public Object getAttribute(String name)
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public Object getAttribute(String name)
    {
=======
    public Object getAttribute(String name) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        return _attributes.getAttribute(name);
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public Set<String> getAttributeNames()
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public Set<String> getAttributeNames()
    {
=======
    public Set<String> getAttributeNames() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        return _attributes.getAttributeNameSet();
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public Object removeAttribute(String name)
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public Object removeAttribute(String name)
    {
=======
    public Object removeAttribute(String name) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        Object old = _attributes.getAttribute(name);
        _attributes.removeAttribute(name);
        return old;
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    @Override
    public void setAttribute(String name, Object value)
    {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    public void setAttribute(String name, Object value)
    {
=======
    public void setAttribute(String name, Object value) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        _attributes.setAttribute(name, value);
    }

    @Override
    public void remoteCall(String target, Object data, MessageListener callback)
    {
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

    protected abstract void send(Message.Mutable message);

    protected Message.Mutable newMessage()
    {
        return new HashMapMessage();
    }

    protected void resetSubscriptions() {
        for (AbstractSessionChannel ch : _channels.values()) {
            ch.resetSubscriptions();
        }
    }

    /**
     * <p>Receives a message (from the server) and process it.</p>
     * <p>Processing the message involves calling the receive {@link Extension extensions}
     * and the channel {@link ClientSessionChannel.ClientSessionChannelListener listeners}.</p>
     *
     * @param message the message received.
     */
    public void receive(final Message.Mutable message) {
        String channelName = message.getChannel();
        if (channelName == null) {
            throw new IllegalArgumentException("Bayeux messages must have a channel, " + message);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        if (Channel.META_SUBSCRIBE.equals(channelName))
        {
            // Remove the subscriber if the subscription fails.
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        if (Channel.META_SUBSCRIBE.equals(channelName))
        {
=======
        if (Channel.META_SUBSCRIBE.equals(channelName)) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            ClientSessionChannel.MessageListener subscriber = unregisterSubscriber(message.getId());
            if (!message.isSuccessful()) {
                String subscription = (String)message.get(Message.SUBSCRIPTION_FIELD);
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

    private boolean handleRemoteCall(Message.Mutable message)
    {
        String messageId = message.getId();
        if (messageId != null)
        {
            MessageListener listener = _remoteCalls.remove(messageId);
            if (listener != null)
            {
                notifyMessageListener(listener, message);
                return true;
            }
        }
        return false;
    }

    private void notifyMessageListener(MessageListener listener, Message.Mutable message)
    {
        try
        {
            listener.onMessage(message);
        }
        catch (Throwable x)
        {
            logger.info("Exception while invoking listener " + listener, x);
        }
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    protected void notifyListeners(Message.Mutable message)
    {
        if (message.isMeta() || message.isPublishReply())
        {
            String messageId = message.getId();
            ClientSessionChannel.MessageListener callback = unregisterCallback(messageId);
            if (callback != null)
                notifyListener(callback, message);
        }

||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    protected void notifyListeners(Message.Mutable message)
    {
=======
    protected void notifyListeners(Message.Mutable message) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
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
        // Use getChannels().get(channelName) instead of getChannel(channelName)
        // to avoid to cache channels that can be released immediately.

        AbstractSessionChannel channel = ChannelId.isMeta(id) ? (AbstractSessionChannel)getChannel(id) : getChannels().get(id);
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

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    protected ClientSessionChannel.MessageListener unregisterCallback(String messageId)
    {
        if (messageId == null)
            return null;
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    protected ClientSessionChannel.MessageListener unregisterCallback(String messageId)
    {
=======
    protected ClientSessionChannel.MessageListener unregisterCallback(String messageId) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        return _callbacks.remove(messageId);
    }

    protected void registerSubscriber(String messageId, ClientSessionChannel.MessageListener subscriber) {
        if (subscriber != null) {
            _subscribers.put(messageId, subscriber);
        }
    }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
    protected ClientSessionChannel.MessageListener unregisterSubscriber(String messageId)
    {
        if (messageId == null)
            return null;
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
    protected ClientSessionChannel.MessageListener unregisterSubscriber(String messageId)
    {
=======
    protected ClientSessionChannel.MessageListener unregisterSubscriber(String messageId) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
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

    /**
     * <p>A channel scoped to a {@link ClientSession}.</p>
     */
    protected abstract class AbstractSessionChannel implements ClientSessionChannel, Dumpable {
        private final ChannelId _id;
        private final AttributesMap _attributes = new AttributesMap();
        private final CopyOnWriteArrayList<MessageListener> _subscriptions = new CopyOnWriteArrayList<>();
        private final AtomicInteger _subscriptionCount = new AtomicInteger();
        private final CopyOnWriteArrayList<ClientSessionChannelListener> _listeners = new CopyOnWriteArrayList<>();
        private volatile boolean _released;

        protected AbstractSessionChannel(ChannelId id) {
            _id = id;
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public ChannelId getChannelId()
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public ChannelId getChannelId()
        {
=======
        public ChannelId getChannelId() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            return _id;
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void addListener(ClientSessionChannelListener listener)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void addListener(ClientSessionChannelListener listener)
        {
=======
        public void addListener(ClientSessionChannelListener listener) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            throwIfReleased();
            _listeners.add(listener);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void removeListener(ClientSessionChannelListener listener)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void removeListener(ClientSessionChannelListener listener)
        {
=======
        public void removeListener(ClientSessionChannelListener listener) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            throwIfReleased();
            _listeners.remove(listener);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public List<ClientSessionChannelListener> getListeners()
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public List<ClientSessionChannelListener> getListeners()
        {
=======
        public List<ClientSessionChannelListener> getListeners() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            return Collections.unmodifiableList(_listeners);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void publish(Object data)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void publish(Object data)
        {
=======
        public void publish(Object data) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            publish(data, null);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void publish(Object data, MessageListener callback)
        {
            throwIfReleased();
            Message.Mutable message = newMessage();
            String messageId = newMessageId();
            message.setId(messageId);
            message.setChannel(getId());
            message.setData(data);
            registerCallback(messageId, callback);
            send(message);
        }

        @Override
        public void subscribe(MessageListener listener)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void subscribe(MessageListener listener)
        {
=======
        public void subscribe(MessageListener listener) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            subscribe(listener, null);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void subscribe(MessageListener listener, MessageListener callback)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void subscribe(MessageListener listener, MessageListener callback)
        {
=======
        public void subscribe(MessageListener listener, MessageListener callback) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            throwIfReleased();
            boolean added = _subscriptions.add(listener);
            if (added) {
                int count = _subscriptionCount.incrementAndGet();
                if (count == 1) {
                    sendSubscribe(listener, callback);
                }
            }
        }

        protected void sendSubscribe(MessageListener listener, MessageListener callback)
        {
            Message.Mutable message = newMessage();
            String messageId = newMessageId();
            message.setId(messageId);
            message.setChannel(Channel.META_SUBSCRIBE);
            message.put(Message.SUBSCRIPTION_FIELD, getId());
            registerSubscriber(messageId, listener);
            registerCallback(messageId, callback);
            send(message);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void unsubscribe(MessageListener listener)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void unsubscribe(MessageListener listener)
        {
=======
        public void unsubscribe(MessageListener listener) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            unsubscribe(listener, null);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void unsubscribe(MessageListener listener, MessageListener callback)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void unsubscribe(MessageListener listener, MessageListener callback)
        {
=======
        public void unsubscribe(MessageListener listener, MessageListener callback) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
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

        protected void sendUnSubscribe(MessageListener callback)
        {
            Message.Mutable message = newMessage();
            String messageId = newMessageId();
            message.setId(messageId);
            message.setChannel(Channel.META_UNSUBSCRIBE);
            message.put(Message.SUBSCRIPTION_FIELD, getId());
            registerCallback(messageId, callback);
            send(message);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void unsubscribe()
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void unsubscribe()
        {
=======
        public void unsubscribe() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            throwIfReleased();
            for (MessageListener listener : _subscriptions) {
                unsubscribe(listener);
            }
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public List<MessageListener> getSubscribers()
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public List<MessageListener> getSubscribers()
        {
=======
        public List<MessageListener> getSubscribers() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            return Collections.unmodifiableList(_subscriptions);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public boolean release()
        {
            if (_released)
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public boolean release()
        {
            if (_released)
=======
        public boolean release() {
            if (_released) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
                return false;
            }

            if (_subscriptions.isEmpty() && _listeners.isEmpty()) {
                boolean removed = _channels.remove(getId(), this);
                _released = removed;
                return removed;
            }
            return false;
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public boolean isReleased()
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public boolean isReleased()
        {
=======
        public boolean isReleased() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            return _released;
        }

        protected void resetSubscriptions() {
            throwIfReleased();
            for (MessageListener l : _subscriptions) {
                if (_subscriptions.remove(l)) {
                    _subscriptionCount.decrementAndGet();
<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
            }
        }

        @Override
        public String getId()
        {
            return _id.toString();
        }

        @Override
        public boolean isDeepWild()
        {
            return _id.isDeepWild();
        }

        @Override
        public boolean isMeta()
        {
            return _id.isMeta();
        }

        @Override
        public boolean isService()
        {
            return _id.isService();
        }

        @Override
        public boolean isBroadcast()
        {
            return !isMeta() && !isService();
        }

        @Override
        public boolean isWild()
        {
            return _id.isWild();
        }

        protected void notifyMessageListeners(Message message)
        {
            throwIfReleased();
            for (ClientSessionChannelListener listener : _listeners)
            {
                if (listener instanceof ClientSessionChannel.MessageListener)
                    notifyOnMessage((MessageListener)listener, message);
            }
            for (ClientSessionChannelListener listener : _subscriptions)
            {
                if (listener instanceof ClientSessionChannel.MessageListener)
                {
                    if (!message.isPublishReply())
                        notifyOnMessage((MessageListener)listener, message);
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
            }
        }

        public String getId()
        {
            return _id.toString();
        }

        public boolean isDeepWild()
        {
            return _id.isDeepWild();
        }

        public boolean isMeta()
        {
            return _id.isMeta();
        }

        public boolean isService()
        {
            return _id.isService();
        }

        public boolean isBroadcast()
        {
            return !isMeta() && !isService();
        }

        public boolean isWild()
        {
            return _id.isWild();
        }

        protected void notifyMessageListeners(Message message)
        {
            throwIfReleased();
            for (ClientSessionChannelListener listener : _listeners)
            {
                if (listener instanceof ClientSessionChannel.MessageListener)
                    notifyOnMessage((MessageListener)listener, message);
            }
            for (ClientSessionChannelListener listener : _subscriptions)
            {
                if (listener instanceof ClientSessionChannel.MessageListener)
                {
                    if (!message.isPublishReply())
                        notifyOnMessage((MessageListener)listener, message);
=======
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
                }
            }
        }

        public String getId() {
            return _id.toString();
        }

        public boolean isDeepWild() {
            return _id.isDeepWild();
        }

        public boolean isMeta() {
            return _id.isMeta();
        }

        public boolean isService() {
            return _id.isService();
        }

        public boolean isBroadcast() {
            return !isMeta() && !isService();
        }

        public boolean isWild() {
            return _id.isWild();
        }

        protected void notifyMessageListeners(Message message) {
            throwIfReleased();
            for (ClientSessionChannelListener listener : _listeners) {
                if (listener instanceof ClientSessionChannel.MessageListener) {
                    notifyOnMessage((MessageListener)listener, message);
                }
            }
            for (ClientSessionChannelListener listener : _subscriptions) {
                if (listener instanceof ClientSessionChannel.MessageListener) {
                    if (!message.isPublishReply()) {
                        notifyOnMessage((MessageListener)listener, message);
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

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public void setAttribute(String name, Object value)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public void setAttribute(String name, Object value)
        {
=======
        public void setAttribute(String name, Object value) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            throwIfReleased();
            _attributes.setAttribute(name, value);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public Object getAttribute(String name)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public Object getAttribute(String name)
        {
=======
        public Object getAttribute(String name) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            throwIfReleased();
            return _attributes.getAttribute(name);
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public Set<String> getAttributeNames()
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public Set<String> getAttributeNames()
        {
=======
        public Set<String> getAttributeNames() {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
            throwIfReleased();
            return _attributes.getAttributeNameSet();
        }

<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        @Override
        public Object removeAttribute(String name)
        {
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public Object removeAttribute(String name)
        {
=======
        public Object removeAttribute(String name) {
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
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
<<<<<<< commits-rmx_100/cometd/cometd/93cfd4f8a0c4268e07f03d4a698762431d103d5b/AbstractClientSession-0557e68.java
        public String toString()
        {
            return String.format("%s@%s", _id, AbstractClientSession.this);
||||||| commits-rmx_100/cometd/cometd/8354b67058d9d34baf074f5b3630e642088a4b19/AbstractClientSession-af69834.java
        public String toString()
        {
            return _id.toString();
=======
        public String toString() {
            return _id.toString();
>>>>>>> commits-rmx_100/cometd/cometd/ed602f7e76bf25729bd83695f42d4198489c345f/AbstractClientSession-b707cea.java
        }
    }
}
