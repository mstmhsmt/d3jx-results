package twitter4j;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import twitter4j.http.Authorization;
import twitter4j.internal.http.HttpClientWrapper;
import twitter4j.internal.http.HttpClientWrapperConfiguration;
import twitter4j.internal.http.HttpParameter;
import twitter4j.internal.logging.Logger;
import java.io.IOException;
import java.util.Map;

final public class TwitterStream extends TwitterBase implements java.io.Serializable {

    private final HttpClientWrapper http;

    private final static Logger logger = Logger.getLogger(TwitterStream.class);

    private StatusListener statusListener;

    private StreamHandlingThread handler = null;

    private final static long serialVersionUID = -762817147320767897L;

    public TwitterStream() {
        super(ConfigurationContext.getInstance());
        http = new HttpClientWrapper(new StreamingReadTimeoutConfiguration(conf));
        ensureBasicEnabled();
    }

    public TwitterStream(String screenName, String password) {
        super(ConfigurationContext.getInstance(), screenName, password);
        http = new HttpClientWrapper(new StreamingReadTimeoutConfiguration(conf));
        ensureBasicEnabled();
    }

    public TwitterStream(String screenName, String password, StatusListener listener) {
        super(ConfigurationContext.getInstance(), screenName, password);
        this.statusListener = listener;
        http = new HttpClientWrapper(new StreamingReadTimeoutConfiguration(conf));
        ensureBasicEnabled();
    }

    TwitterStream(Configuration conf, Authorization auth, StatusListener listener) {
        super(conf, auth);
        http = new HttpClientWrapper(new StreamingReadTimeoutConfiguration(conf));
        this.statusListener = listener;
        ensureBasicEnabled();
    }

    public void firehose(final int count) {
        startHandler(new StreamHandlingThread() {

            public StatusStream getStream() throws TwitterException {
                return getFirehoseStream(count);
            }
        });
    }

    public StatusStream getFirehoseStream(int count) throws TwitterException {
        return getCountStream("statuses/firehose.json", count);
    }

    public void links(final int count) {
        startHandler(new StreamHandlingThread() {

            public StatusStream getStream() throws TwitterException {
                return getLinksStream(count);
            }
        });
    }

    public StatusStream getLinksStream(int count) throws TwitterException {
        return getCountStream("statuses/links.json", count);
    }

    public void retweet() {
        ensureBasicEnabled();
        startHandler(new StreamHandlingThread() {

            public StatusStream getStream() throws TwitterException {
                return getRetweetStream();
            }
        });
    }

    public StatusStream getRetweetStream() throws TwitterException {
        ensureBasicEnabled();
        try {
            return new StatusStreamImpl(http.post(conf.getStreamBaseURL() + "statuses/retweet.json", new HttpParameter[] {}, auth));
        } catch (IOException e) {
            throw new TwitterException(e);
        }
    }

    public void sample() {
        ensureBasicEnabled();
        startHandler(new StreamHandlingThread() {

            public StatusStream getStream() throws TwitterException {
                return getSampleStream();
            }
        });
    }

    public StatusStream getSampleStream() throws TwitterException {
        ensureBasicEnabled();
        try {
            return new StatusStreamImpl(http.get(conf.getStreamBaseURL() + "statuses/sample.json", auth));
        } catch (IOException e) {
            throw new TwitterException(e);
        }
    }

    public void filter(final int count, final int[] follow, final String[] track) {
        startHandler(new StreamHandlingThread() {

            public StatusStream getStream() throws TwitterException {
                return getFilterStream(count, follow, track);
            }
        });
    }

    private synchronized void startHandler(StreamHandlingThread handler) {
        cleanup();
        if (null == statusListener) {
            throw new IllegalStateException("StatusListener is not set.");
        }
        this.handler = handler;
        this.handler.start();
    }

    public synchronized void cleanup() {
        if (null != handler) {
            try {
                handler.close();
            } catch (IOException ignore) {
            }
        }
    }

    public void setStatusListener(StatusListener statusListener) {
        this.statusListener = statusListener;
    }

    private final static int TCP_ERROR_INITIAL_WAIT = 250;

    private final static int TCP_ERROR_WAIT_CAP = 16 * 1000;

    private final static int HTTP_ERROR_INITIAL_WAIT = 10 * 1000;

    private final static int HTTP_ERROR_WAIT_CAP = 240 * 1000;

    abstract class StreamHandlingThread extends Thread {

        private final static String NAME = "Twitter Stream Handling Thread";

        private boolean closed = false;

        private void setStatus(String message) {
            String actualMessage = NAME + message;
            setName(actualMessage);
            logger.debug(actualMessage);
        }

        abstract StatusStream getStream() throws TwitterException;

        private StatusStream stream = null;

        private UserStreamListener userStreamListener;

        private final boolean handleUserStream;

        StreamHandlingThread() {
            this(false);
        }

        StreamHandlingThread(boolean handleUserStream) {
            super(NAME + "[initializing]");
            this.handleUserStream = handleUserStream;
        }

        public void run() {
            int timeToSleep = NO_WAIT;
            while (!closed) {
                try {
                    if (!closed && null == stream) {
                        setStatus("[Establishing connection]");
                        stream = getStream();
                        timeToSleep = NO_WAIT;
                        setStatus("[Receiving stream]");
                        while (!closed) {
                            if (handleUserStream) {
                                ((UserStream) stream).next((UserStreamListener) statusListener);
                            } else {
                                stream.next(statusListener);
                            }
                        }
                    }
                } catch (TwitterException te) {
                    if (!closed) {
                        if (NO_WAIT == timeToSleep) {
                            if (te.getStatusCode() > 200) {
                                timeToSleep = HTTP_ERROR_INITIAL_WAIT;
                            } else {
                                timeToSleep = TCP_ERROR_INITIAL_WAIT;
                            }
                        }
                        if (!closed) {
                            setStatus("[Waiting for " + (timeToSleep) + " milliseconds]");
                            try {
                                Thread.sleep(timeToSleep);
                            } catch (InterruptedException ignore) {
                            }
                            timeToSleep = Math.min(timeToSleep * 2, (te.getStatusCode() > 200) ? HTTP_ERROR_WAIT_CAP : TCP_ERROR_WAIT_CAP);
                        }
                        stream = null;
                        logger.debug(te.getMessage());
                        statusListener.onException(te);
                    }
                }
            }
            try {
                this.stream.close();
            } catch (IOException ignore) {
            }
        }

        public synchronized void close() throws IOException {
            setStatus("[Disposing thread]");
            closed = true;
        }
    }

    public StatusStream getFilterStream(int count, int[] follow, String[] track) throws TwitterException {
        return getFilterStream(new FilterQuery(count, follow, track, null));
    }

    public void setUserStreamListener(UserStreamListener statusListener) {
        this.statusListener = statusListener;
    }

    public void stream(final String relativeUrl, final int count) {
        startHandler(new StreamHandlingThread() {

            public StatusStream getStream() throws TwitterException {
                return getCountStream(relativeUrl, count);
            }
        });
    }

    private final static int NO_WAIT = 0;

    public void filter(final FilterQuery query) throws TwitterException {
        startHandler(new StreamHandlingThread() {

            public StatusStream getStream() throws TwitterException {
                return getFilterStream(query);
            }
        });
    }

    private synchronized void startUserStreamHandler(StreamHandlingThread handler) {
        cleanup();
        if (null == statusListener) {
            throw new IllegalStateException("UserStreamListener is not set.");
        }
        if (!(statusListener instanceof UserStreamListener)) {
            throw new IllegalStateException("UserStreamListener is not set.");
        }
        this.handler = handler;
        this.handler.start();
    }

    public void user() {
        ensureBasicEnabled();
        startHandler(new StreamHandlingThread(true) {

            public UserStream getStream() throws TwitterException {
                return getUserStream();
            }
        });
    }

    public UserStream getUserStream() throws TwitterException {
        ensureBasicEnabled();
        if (!(statusListener instanceof UserStreamListener)) {
            logger.warn("Use of UserStreamListener is suggested.");
        }
        try {
            return new StatusStreamImpl(http.get(conf.getUserStreamBaseURL() + "user.json", auth));
        } catch (IOException e) {
            throw new TwitterException(e);
        }
    }

    public StatusStream getFilterStream(FilterQuery query) throws TwitterException {
        ensureBasicEnabled();
        try {
            return new StatusStreamImpl(http.post(conf.getStreamBaseURL() + "statuses/filter.json", query.asHttpParameterArray(), auth));
        } catch (IOException e) {
            throw new TwitterException(e);
        }
    }

    private StatusStream getCountStream(String relativeUrl, int count) throws TwitterException {
        ensureBasicEnabled();
        try {
            return new StatusStreamImpl(http.post(conf.getStreamBaseURL() + relativeUrl, new HttpParameter[] { new HttpParameter("count", String.valueOf(count)) }, auth));
        } catch (IOException e) {
            throw new TwitterException(e);
        }
    }
}

class StreamingReadTimeoutConfiguration implements HttpClientWrapperConfiguration {

    Configuration nestedConf;

    StreamingReadTimeoutConfiguration(Configuration httpConf) {
        this.nestedConf = httpConf;
    }

    public String getHttpProxyHost() {
        return nestedConf.getHttpProxyHost();
    }

    public int getHttpProxyPort() {
        return nestedConf.getHttpProxyPort();
    }

    public String getHttpProxyUser() {
        return nestedConf.getHttpProxyUser();
    }

    public String getHttpProxyPassword() {
        return nestedConf.getHttpProxyPassword();
    }

    public int getHttpConnectionTimeout() {
        return nestedConf.getHttpConnectionTimeout();
    }

    public int getHttpReadTimeout() {
        return nestedConf.getHttpStreamingReadTimeout();
    }

    public int getHttpRetryCount() {
        return nestedConf.getHttpRetryCount();
    }

    public int getHttpRetryIntervalSeconds() {
        return nestedConf.getHttpRetryIntervalSeconds();
    }

    public Map<String, String> getRequestHeaders() {
        return nestedConf.getRequestHeaders();
    }
}
