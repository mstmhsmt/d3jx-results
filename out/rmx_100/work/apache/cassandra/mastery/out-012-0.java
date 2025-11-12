package org.apache.cassandra.transport;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.util.AttributeKey;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.net.FrameEncoder;
import org.apache.cassandra.service.ClientWarn;
import org.apache.cassandra.service.QueryState;
import org.apache.cassandra.transport.Flusher.FlushItem;
import org.apache.cassandra.transport.messages.ErrorMessage;
import org.apache.cassandra.transport.messages.EventMessage;
import org.apache.cassandra.utils.JVMStabilityInspector;
import static org.apache.cassandra.concurrent.SharedExecutorPool.SHARED;
import com.google.common.annotations.VisibleForTesting;
import org.apache.cassandra.utils.NoSpamLogger;
import org.apache.cassandra.utils.MonotonicClock;
import org.apache.cassandra.transport.ClientResourceLimits.Overload;
import org.apache.cassandra.service.reads.thresholds.CoordinatorWarnings;
import org.apache.cassandra.metrics.ClientMetrics;
import org.apache.cassandra.exceptions.OverloadedException;
import org.apache.cassandra.concurrent.LocalAwareExecutorPlus;
import org.apache.cassandra.concurrent.DebuggableTask;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.google.common.base.Predicate;
import java.util.concurrent.TimeUnit;

public class Dispatcher implements CQLMessageHandler.MessageConsumer<Message.Request> {

    @VisibleForTesting
    static final LocalAwareExecutorPlus requestExecutor = SHARED.newExecutor(DatabaseDescriptor.getNativeTransportMaxThreads(), DatabaseDescriptor::setNativeTransportMaxThreads, "transport", "Native-Transport-Requests");

    private static final ConcurrentMap<EventLoop, Flusher> flusherLookup = new ConcurrentHashMap<>();

    private final boolean useLegacyFlusher;

    interface FlushItemConverter {

        FlushItem<?> toFlushItem(Channel channel, Message.Request request, Message.Response response);
    }

    public Dispatcher(boolean useLegacyFlusher) {
        this.useLegacyFlusher = useLegacyFlusher;
    }

    @Override
    public void dispatch(Channel channel, Message.Request request, FlushItemConverter forFlusher, Overload backpressure) {
        
requestExecutor.submit(new RequestProcessor(channel, request, forFlusher, backpressure));ClientMetrics.instance.markRequestDispatched();

        executor.submit(() -> processRequest(channel, request, forFlusher));
    }

    private void flush(FlushItem<?> item) {
        EventLoop loop = item.channel.eventLoop();
        Flusher flusher = flusherLookup.get(loop);
        if (flusher == null) {
            Flusher created = useLegacyFlusher ? Flusher.legacy(loop) : Flusher.immediate(loop);
            Flusher alt = flusherLookup.putIfAbsent(loop, flusher = created);
            if (alt != null)
                flusher = alt;
        }
        flusher.enqueue(item);
        flusher.start();
    }

    static public void shutdown() {
        requestExecutor.shutdown();
        authExecutor.shutdown();
    }

    static final AttributeKey<Consumer<EventMessage>> EVENT_DISPATCHER = AttributeKey.valueOf("EVTDISP");

    Consumer<EventMessage> eventDispatcher(final Channel channel, final ProtocolVersion version, final FrameEncoder.PayloadAllocator allocator) {
        return eventMessage -> flush(new FlushItem.Framed(channel, eventMessage.encode(version), null, allocator, f -> f.response.release()));
    }

    public class RequestProcessor implements DebuggableTask.RunnableDebuggableTask {

        private final Channel channel;

        private final Message.Request request;

        private final FlushItemConverter forFlusher;

        private final Overload backpressure;

        private volatile long startTimeNanos;

        public RequestProcessor(Channel channel, Message.Request request, FlushItemConverter forFlusher, Overload backpressure) {
            this.channel = channel;
            this.request = request;
            this.forFlusher = forFlusher;
            this.backpressure = backpressure;
        }

        @Override
        public void run() {
            startTimeNanos = MonotonicClock.Global.preciseTime.now();
            processRequest(channel, request, forFlusher, backpressure, new RequestTime(request.createdAtNanos, startTimeNanos));
        }

        @Override
        public long creationTimeNanos() {
            return request.createdAtNanos;
        }

        @Override
        public long startTimeNanos() {
            return startTimeNanos;
        }

        @Override
        public String description() {
            return request.toString();
        }

        @Override
        public String toString() {
            return "RequestProcessor{" + "request=" + request + ", approxStartTimeNanos=" + startTimeNanos + '}';
        }
    }

    static Message.Response processRequest(Channel channel, Message.Request request, Overload backpressure, RequestTime requestTime) {
        try {
            return processRequest((ServerConnection) request.connection(), request, backpressure, requestTime);
        } catch (Throwable t) {
            JVMStabilityInspector.inspectThrowable(t);
            if (request.isTrackable())
                CoordinatorWarnings.done();
            Predicate<Throwable> handler = ExceptionHandlers.getUnexpectedExceptionHandler(channel, true);
            ErrorMessage error = ErrorMessage.fromException(t, handler);
            error.setStreamId(request.getStreamId());
            error.setWarnings(ClientWarn.instance.getWarnings());
            return error;
        } finally {
            CoordinatorWarnings.reset();
            ClientWarn.instance.resetWarnings();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    @Override
    public boolean hasQueueCapacity() {
        double threshold = DatabaseDescriptor.getNativeTransportQueueMaxItemAgeThreshold();
        if (threshold <= 0)
            return true;
        return requestExecutor.oldestTaskQueueTime() < (DatabaseDescriptor.getNativeTransportTimeout(TimeUnit.NANOSECONDS) * threshold);
    }

    void processRequest(Channel channel, Message.Request request, FlushItemConverter forFlusher, Overload backpressure, RequestTime requestTime) {
        Message.Response response = processRequest(channel, request, backpressure, requestTime);
        FlushItem<?> toFlush = forFlusher.toFlushItem(channel, request, response);
        Message.logger.trace("Responding: {}, v={}", response, request.connection().getVersion());
        flush(toFlush);
    }

    @VisibleForTesting
    static final LocalAwareExecutorService authExecutor = SHARED.newExecutor(Math.max(1, DatabaseDescriptor.getNativeTransportMaxAuthThreads()), DatabaseDescriptor::setNativeTransportMaxAuthThreads, "transport", "Native-Transport-Auth-Requests");

    private static Message.Response processRequest(ServerConnection connection, Message.Request request, Overload backpressure, RequestTime requestTime) {
        long queueTime = requestTime.timeSpentInQueueNanos();
        ClientMetrics.instance.queueTime(queueTime, TimeUnit.NANOSECONDS);
        if (queueTime > DatabaseDescriptor.getNativeTransportTimeout(TimeUnit.NANOSECONDS)) {
            ClientMetrics.instance.markTimedOutBeforeProcessing();
            return ErrorMessage.fromException(new OverloadedException("Query timed out before it could start"));
        }
        if (connection.getVersion().isGreaterOrEqualTo(ProtocolVersion.V4))
            ClientWarn.instance.captureWarnings();
        if (request.isTrackable())
            CoordinatorWarnings.init();
        switch(backpressure) {
            case NONE:
                break;
            case REQUESTS:
                {
                    String message = String.format("Request breached global limit of %d requests/second and triggered backpressure.", ClientResourceLimits.getNativeTransportMaxRequestsPerSecond());
                    NoSpamLogger.log(logger, NoSpamLogger.Level.INFO, 1, TimeUnit.MINUTES, message);
                    ClientWarn.instance.warn(message);
                    break;
                }
            case BYTES_IN_FLIGHT:
                {
                    String message = String.format("Request breached limit(s) on bytes in flight (Endpoint: %d, Global: %d) and triggered backpressure.", ClientResourceLimits.getEndpointLimit(), ClientResourceLimits.getGlobalLimit());
                    NoSpamLogger.log(logger, NoSpamLogger.Level.INFO, 1, TimeUnit.MINUTES, message);
                    ClientWarn.instance.warn(message);
                    break;
                }
            case QUEUE_TIME:
                {
                    String message = String.format("Request has spent over %s time of the maximum timeout %dms in the queue", DatabaseDescriptor.getNativeTransportQueueMaxItemAgeThreshold(), DatabaseDescriptor.getNativeTransportTimeout(TimeUnit.MILLISECONDS));
                    NoSpamLogger.log(logger, NoSpamLogger.Level.INFO, 1, TimeUnit.MINUTES, message);
                    ClientWarn.instance.warn(message);
                    break;
                }
        }
        QueryState qstate = connection.validateNewMessage(request.type, connection.getVersion());
        Message.logger.trace("Received: {}, v={}", request, connection.getVersion());
        connection.requests.inc();
        Message.Response response = request.execute(qstate, requestTime);
        if (request.isTrackable())
            CoordinatorWarnings.done();
        response.setStreamId(request.getStreamId());
        response.setWarnings(ClientWarn.instance.getWarnings());
        response.attach(connection);
        connection.applyStateTransition(request.type, response.type);
        return response;
    }

    static public class RequestTime {

        private final long enqueuedAtNanos;

        private final long startedAtNanos;

        public RequestTime(long createdAtNanos) {
            this(createdAtNanos, createdAtNanos);
        }

        public RequestTime(long enqueuedAtNanos, long startedAtNanos) {
            this.enqueuedAtNanos = enqueuedAtNanos;
            this.startedAtNanos = startedAtNanos;
        }

        static public RequestTime forImmediateExecution() {
            return new RequestTime(MonotonicClock.Global.preciseTime.now());
        }

        public long startedAtNanos() {
            return startedAtNanos;
        }

        public long enqueuedAtNanos() {
            return enqueuedAtNanos;
        }

        public long baseTimeNanos() {
            switch(DatabaseDescriptor.getCQLStartTime()) {
                case REQUEST:
                    return startedAtNanos();
                case QUEUE:
                    return enqueuedAtNanos();
                default:
                    throw new IllegalArgumentException("Unknown start time: " + DatabaseDescriptor.getCQLStartTime());
            }
        }

        public long computeDeadline(long verbExpiresAfterNanos) {
            long clientDeadline = clientDeadline();
            long verbDeadline = baseTimeNanos() + verbExpiresAfterNanos;
            return Math.min(verbDeadline, clientDeadline);
        }

        public long computeTimeout(long now, long verbExpiresAfterNanos) {
            return computeDeadline(verbExpiresAfterNanos) - now;
        }

        public boolean shouldSendHints() {
            if (!DatabaseDescriptor.getEnforceNativeDeadlineForHints())
                return true;
            long now = MonotonicClock.Global.preciseTime.now();
            long clientDeadline = clientDeadline();
            return now < clientDeadline;
        }

        public long clientDeadline() {
            return enqueuedAtNanos() + DatabaseDescriptor.getNativeTransportTimeout(TimeUnit.NANOSECONDS);
        }

        public long timeSpentInQueueNanos() {
            return startedAtNanos - enqueuedAtNanos;
        }
    }
}
