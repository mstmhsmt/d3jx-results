package org.cometd.oort;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.LocalSession;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeoutException;
import org.cometd.server.BayeuxServerImpl;
import org.eclipse.jetty.util.thread.Timeout;

public abstract class OortService<R, C> extends AbstractLifeCycle implements ServerChannel.MessageListener {

    private static final String ID_FIELD = "oort.service.id";

    private static final String OORT_URL_FIELD = "oort.service.url";

    private static final String RESULT_FIELD = "oort.service.result";

    private final AtomicLong contextIds = new AtomicLong();

    
<<<<<<< commits-rmx_100/cometd/cometd/ec8df470d53e55d038560ed1521c7944f5bf46c1/OortService-3d9c975.java
private final ConcurrentMap<Long, C> callbacks = new ConcurrentHashMap<>();
=======

>>>>>>> commits-rmx_100/cometd/cometd/2bfee7a6f98a0bf2f8f4cf0d2f7d264a40c8f88c/OortService-cb1075e.java


    private final Oort oort;

    private final String name;

    private final String forwardChannelName;

    private final String resultChannelName;

    private final LocalSession session;

    final protected Logger logger;

    protected OortService(Oort oort, String name) {
        this.oort = oort;
        this.name = name;
        this.forwardChannelName = "/service/oort/service/" + name;
        this.broadcastChannelName = "/oort/service/" + name;
        this.resultChannelName = forwardChannelName + "/result";
        this.session = oort.getBayeuxServer().newLocalSession(name);
        this.logger = LoggerFactory.getLogger(getLoggerName());
    }

    protected String getLoggerName() {
        return getClass().getName();
    }

    public Oort getOort() {
        return oort;
    }

    public String getName() {
        return name;
    }

    public LocalSession getLocalSession() {
        return session;
    }

    @Override
    protected void doStart() throws Exception {
        session.handshake();
        BayeuxServer bayeuxServer = oort.getBayeuxServer();
        bayeuxServer.createIfAbsent(forwardChannelName);
        bayeuxServer.getChannel(forwardChannelName).addListener(this);
        bayeuxServer.createIfAbsent(broadcastChannelName);
        bayeuxServer.getChannel(broadcastChannelName).addListener(this);
        bayeuxServer.createIfAbsent(resultChannelName);
        bayeuxServer.getChannel(resultChannelName).addListener(this);
        oort.observeChannel(broadcastChannelName);
        logger.debug("Started {}", this);
    }

    @Override
    protected void doStop() throws Exception {
        oort.deobserveChannel(broadcastChannelName);
        BayeuxServer bayeuxServer = oort.getBayeuxServer();
        bayeuxServer.getChannel(resultChannelName).removeListener(this);
        bayeuxServer.getChannel(broadcastChannelName).removeListener(this);
        bayeuxServer.getChannel(forwardChannelName).removeListener(this);
        session.disconnect();
        logger.debug("Stopped {}", this);
    }

    protected boolean forward(String targetOortURL, Object parameter, C context) 
<<<<<<< commits-rmx_100/cometd/cometd/ec8df470d53e55d038560ed1521c7944f5bf46c1/OortService-3d9c975.java
{
        long actionId = actions.incrementAndGet();
        if (context != null)
            callbacks.put(actionId, context);
        Map<String, Object> data = new HashMap<>(3);
        data.put(ID_FIELD, actionId);
        data.put(ACTION_FIELD, actionData);
        String localOortURL = getOort().getURL();
        data.put(OORT_URL_FIELD, localOortURL);
        if (localOortURL.equals(targetOortURL)) {
            logger.debug("Forwarding action locally ({}): {}", localOortURL, data);
            oort.getBayeuxServer().getChannel(forwardChannelName).publish(getLocalSession(), data);
            return true;
        } else {
            if (targetOortURL != null) {
                OortComet comet = getOort().getComet(targetOortURL);
                if (comet != null) {
                    logger.debug("Forwarding action from {} to {}: {}", localOortURL, targetOortURL, data);
                    comet.getChannel(forwardChannelName).publish(data);
                    return true;
                }
            }
            logger.debug("Could not forward action from {} to {}: {}", localOortURL, targetOortURL, data);
            return false;
        }
    }
=======

>>>>>>> commits-rmx_100/cometd/cometd/2bfee7a6f98a0bf2f8f4cf0d2f7d264a40c8f88c/OortService-cb1075e.java


    public boolean onMessage(ServerSession from, ServerChannel channel, ServerMessage.Mutable message) {
        
<<<<<<< commits-rmx_100/cometd/cometd/ec8df470d53e55d038560ed1521c7944f5bf46c1/OortService-3d9c975.java
if (forwardChannelName.equals(message.getChannel())) {
            logger.debug("Received forwarded action {}", message);
            Map<String, Object> data = message.getDataAsMap();
            Map<String, Object> resultData = new HashMap<>(3);
            resultData.put(ID_FIELD, data.get(ID_FIELD));
            resultData.put(OORT_URL_FIELD, getOort().getURL());
            try {
                R result = onForward(data.get(ACTION_FIELD));
                resultData.put(RESULT_FIELD, result);
            } catch (ServiceException x) {
                resultData.put(FAILURE_FIELD, x.getFailure());
            } catch (Exception x) {
                String failure = x.getMessage();
                if (failure == null || failure.length() == 0)
                    failure = x.getClass().getName();
                resultData.put(FAILURE_FIELD, failure);
            }
            String oortURL = (String) data.get(OORT_URL_FIELD);
            if (getOort().getURL().equals(oortURL)) {
                logger.debug("Returning forwarded action result {} to local {}", resultData, oortURL);
                oort.getBayeuxServer().getChannel(resultChannelName).publish(getLocalSession(), resultData);
            } else {
                OortComet comet = getOort().getComet(oortURL);
                if (comet != null) {
                    logger.debug("Returning forwarded action result {} to remote {}", resultData, oortURL);
                    comet.getChannel(resultChannelName).publish(resultData);
                } else {
                    logger.debug("Could not return forwarded action result {} to remote {}", resultData, oortURL);
                }
            }
        } else if (resultChannelName.equals(message.getChannel())) {
            logger.debug("Received forwarded action result {}", message);
            Map<String, Object> data = message.getDataAsMap();
            long actionId = ((Number) data.get(ID_FIELD)).longValue();
            C context = callbacks.remove(actionId);
            if (context != null) {
                Object failure = data.get(FAILURE_FIELD);
                if (failure != null) {
                    onForwardFailed(failure, context);
                } else {
                    @SuppressWarnings("unchecked")
                    R result = (R) data.get(RESULT_FIELD);
                    onForwardSucceeded(result, context);
                }
            }
        }
=======
if (forwardChannelName.equals(message.getChannel())) {
            onForwardMessage(message.getDataAsMap(), false);
        } else if (broadcastChannelName.equals(message.getChannel())) {
            onForwardMessage(message.getDataAsMap(), true);
        } else if (resultChannelName.equals(message.getChannel())) {
            onResultMessage(message.getDataAsMap());
        }
>>>>>>> commits-rmx_100/cometd/cometd/2bfee7a6f98a0bf2f8f4cf0d2f7d264a40c8f88c/OortService-cb1075e.java

        return true;
    }

    protected abstract Result<R> onForward(Request request);

    protected abstract void onForwardSucceeded(R result, C context);

    protected abstract void onForwardFailed(Object failure, C context);

    @Override
    public String toString() {
        return String.format("%s[%s]@%s", getClass().getSimpleName(), getName(), getOort().getURL());
    }

    static public class ServerContext {

        private final ServerSession session;

        private final ServerMessage message;

        public ServerContext(ServerSession session, ServerMessage message) {
            this.session = session;
            this.message = message;
        }

        public ServerSession getServerSession() {
            return session;
        }

        public ServerMessage getServerMessage() {
            return message;
        }
    }

    private volatile long timeout = 5000;

    protected void onForwardMessage(Map<String, Object> data, boolean broadcast) {
        logger.debug("Received {} action {}", broadcast ? "broadcast" : "forwarded", data);
        Map<String, Object> resultData = new HashMap<String, Object>(3);
        resultData.put(ID_FIELD, data.get(ID_FIELD));
        resultData.put(OORT_URL_FIELD, getOort().getURL());
        String oortURL = (String) data.get(OORT_URL_FIELD);
        try {
            Result<R> result = onForward(new Request(oort.getURL(), data.get(PARAMETER_FIELD), oortURL));
            logger.debug("Forwarded action result {}", result);
            if (result.succeeded()) {
                resultData.put(RESULT_FIELD, true);
                resultData.put(DATA_FIELD, result.data);
            } else if (result.failed()) {
                resultData.put(RESULT_FIELD, false);
                resultData.put(DATA_FIELD, result.data);
            } else {
                if (broadcast) {
                    logger.debug("Ignoring broadcast action result {}", result);
                    return;
                } else {
                    resultData.put(RESULT_FIELD, false);
                    resultData.put(DATA_FIELD, result.data);
                }
            }
        } catch (Exception x) {
            if (broadcast)
                return;
            String failure = x.getMessage();
            if (failure == null || failure.length() == 0)
                failure = x.getClass().getName();
            resultData.put(RESULT_FIELD, false);
            resultData.put(DATA_FIELD, failure);
        }
        if (getOort().getURL().equals(oortURL)) {
            logger.debug("Returning forwarded action result {} to local {}", resultData, oortURL);
            onResultMessage(resultData);
        } else {
            OortComet comet = getOort().getComet(oortURL);
            if (comet != null) {
                logger.debug("Returning forwarded action result {} to remote {}", resultData, oortURL);
                comet.getChannel(resultChannelName).publish(resultData);
            } else {
                logger.debug("Could not return forwarded action result {} to remote {}", resultData, oortURL);
            }
        }
    }

    public long getTimeout() {
        return timeout;
    }

    private final String broadcastChannelName;

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    static public class Result<U> {

        private final Boolean result;

        private final Object data;

        private Result(Boolean result, Object data) {
            this.result = result;
            this.data = data;
        }

        static public <S> Result<S> success(S result) {
            return new Result<S>(true, result);
        }

        static public <S> Result<S> failure(Object failure) {
            return new Result<S>(false, failure);
        }

        static public <S> Result<S> ignore(Object data) {
            return new Result<S>(null, data);
        }

        private boolean succeeded() {
            return result != null && result;
        }

        private boolean failed() {
            return result != null && !result;
        }

        @Override
        public String toString() {
            return String.format("%s[%s] %s", getClass().getSimpleName(), result == null ? "ignored" : result ? "success" : "failure", data);
        }
    }

    private final ConcurrentMap<Long, Map<String, Object>> callbacks = new ConcurrentHashMap<Long, Map<String, Object>>();

    private void startTimeout(Map<String, Object> ctx) {
        long contextId = (Long) ctx.get(ID_FIELD);
        Timeout.Task timeoutTask = new TimeoutTask(contextId);
        ctx.put(TIMEOUT_FIELD, timeoutTask);
        ((BayeuxServerImpl) oort.getBayeuxServer()).startTimeout(timeoutTask, getTimeout());
    }

    private void cancelTimeout(Map<String, Object> ctx) {
        Timeout.Task timeoutTask = (Timeout.Task) ctx.get(TIMEOUT_FIELD);
        if (timeoutTask != null)
            ((BayeuxServerImpl) oort.getBayeuxServer()).cancelTimeout(timeoutTask);
    }

    private static final String TIMEOUT_FIELD = "oort.service.timeout";

    private static final String PARAMETER_FIELD = "oort.service.parameter";

    protected void onResultMessage(Map<String, Object> data) {
        long actionId = ((Number) data.get(ID_FIELD)).longValue();
        Map<String, Object> ctx = callbacks.remove(actionId);
        logger.debug("Action result {} with context {}", data, ctx);
        if (ctx != null) {
            cancelTimeout(ctx);
            @SuppressWarnings("unchecked")
            C context = (C) ctx.get(CONTEXT_FIELD);
            boolean success = (Boolean) data.get(RESULT_FIELD);
            if (success) {
                @SuppressWarnings("unchecked")
                R result = (R) data.get(DATA_FIELD);
                onForwardSucceeded(result, context);
            } else {
                Object failure = data.get(DATA_FIELD);
                onForwardFailed(failure, context);
            }
        }
    }

    private static final String DATA_FIELD = "oort.service.data";

    static public class Request {

        private final String localOortURL;

        private final Object data;

        private final String oortURL;

        private Request(String localOortURL, Object data, String oortURL) {
            this.localOortURL = localOortURL;
            this.data = data;
            this.oortURL = oortURL;
        }

        public Object getData() {
            return data;
        }

        public Map<String, Object> getDataAsMap() {
            return (Map<String, Object>) getData();
        }

        public String getOortURL() {
            return oortURL;
        }

        public boolean isLocal() {
            return localOortURL.equals(getOortURL());
        }
    }

    private static final String CONTEXT_FIELD = "oort.service.context";

    private class TimeoutTask extends Timeout.Task {

        private final long contextId;

        private TimeoutTask(long contextId) {
            this.contextId = contextId;
        }

        @Override
        public void expired() {
            Map<String, Object> data = new HashMap<String, Object>(3);
            data.put(ID_FIELD, contextId);
            data.put(RESULT_FIELD, false);
            data.put(DATA_FIELD, new TimeoutException());
            onResultMessage(data);
        }
    }
}
