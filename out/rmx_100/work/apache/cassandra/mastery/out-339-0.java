package org.apache.cassandra.db.commitlog;

import org.apache.cassandra.concurrent.NamedThreadFactory;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.db.commitlog.CommitLogSegment.Allocation;
import org.apache.cassandra.utils.Clock;
import org.apache.cassandra.utils.NoSpamLogger;
import org.apache.cassandra.utils.concurrent.WaitQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import com.codahale.metrics.Timer.Context;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.concurrent.locks.LockSupport;
import com.google.common.annotations.VisibleForTesting;

public abstract class AbstractCommitLogService {

    static final long DEFAULT_MARKER_INTERVAL_MILLIS = 100;

    private volatile Thread thread;

    private volatile boolean shutdown = false;

    volatile protected long lastSyncedAt = System.currentTimeMillis();

    final private AtomicLong written = new AtomicLong(0);

    final protected AtomicLong pending = new AtomicLong(0);

    final protected WaitQueue syncComplete = new WaitQueue();

    final CommitLog commitLog;

    final private String name;

    final long syncIntervalNanos;

    private volatile boolean syncRequested;

    static final private Logger logger = LoggerFactory.getLogger(AbstractCommitLogService.class);

    AbstractCommitLogService(final CommitLog commitLog, final String name, long syncIntervalMillis) {
        this(commitLog, name, syncIntervalMillis, false);
    }

    AbstractCommitLogService(final CommitLog commitLog, final String name, long syncIntervalMillis, boolean markHeadersFaster) {
        this.commitLog = commitLog;
        this.name = name;
        final long markerIntervalMillis;
        if (markHeadersFaster && syncIntervalMillis > DEFAULT_MARKER_INTERVAL_MILLIS) {
            markerIntervalMillis = DEFAULT_MARKER_INTERVAL_MILLIS;
            long modulo = syncIntervalMillis % markerIntervalMillis;
            if (modulo != 0) {
                syncIntervalMillis -= modulo;
                if (modulo >= markerIntervalMillis / 2)
                    syncIntervalMillis += markerIntervalMillis;
            }
            logger.debug("Will update the commitlog markers every {}ms and flush every {}ms", markerIntervalMillis, syncIntervalMillis);
        } else {
            markerIntervalMillis = syncIntervalMillis;
        }
        assert syncIntervalMillis % markerIntervalMillis == 0;
        this.markerIntervalNanos = TimeUnit.NANOSECONDS.convert(markerIntervalMillis, TimeUnit.MILLISECONDS);
        this.syncIntervalNanos = TimeUnit.NANOSECONDS.convert(syncIntervalMillis, TimeUnit.MILLISECONDS);
    }

    void start() {
        if (syncIntervalNanos < 1)
            throw new IllegalArgumentException(String.format("Commit log flush interval must be positive: %fms", syncIntervalNanos * 1e-6));
        shutdown = false;
        Runnable runnable = new SyncRunnable(new Clock());
        thread = NamedThreadFactory.createThread(runnable, name);
        thread.start();
    }

    class SyncRunnable implements Runnable {

        final private Clock clock;

        private long firstLagAt = 0;

        private long totalSyncDuration = 0;

        private long syncExceededIntervalBy = 0;

        private int lagCount = 0;

        private int syncCount = 0;

        SyncRunnable(Clock clock) {
            this.clock = clock;
        }

        public void run() {
            while (true) {
                if (!sync())
                    break;
            }
        }

        boolean sync() {
            boolean shutdownRequested = shutdown;
            try {
                long pollStarted = clock.nanoTime();
                boolean flushToDisk = lastSyncedAt + syncIntervalMillis <= pollStarted || shutdown || syncRequested;
                
if (lastSyncedAt + syncIntervalNanos <= pollStarted || shutdownRequested || syncRequested) {
                    syncRequested = false;
                    commitLog.sync(true);
                    lastSyncedAt = pollStarted;
                    syncComplete.signalAll();
                    syncCount++;
                } else {
                    commitLog.sync(false);
                }long now = clock.nanoTime();long wakeUpAt = pollStarted + markerIntervalNanos;if (wakeUpAt < now) {
                    if (firstLagAt == 0) {
                        firstLagAt = now;
                        totalSyncDuration = syncExceededIntervalBy = syncCount = lagCount = 0;
                    }
                    syncExceededIntervalBy += now - wakeUpAt;
                    lagCount++;
                }if (firstLagAt > 0) {
                    boolean logged = NoSpamLogger.log(logger, NoSpamLogger.Level.WARN, 5, TimeUnit.MINUTES, "Out of {} commit log syncs over the past {}s with average duration of {}ms, {} have exceeded the configured commit interval by an average of {}ms", syncCount, String.format("%.2f", (now - firstLagAt) * 1e-9d), String.format("%.2f", totalSyncDuration * 1e-6d / syncCount), lagCount, String.format("%.2f", syncExceededIntervalBy * 1e-6d / lagCount));
                    if (logged)
                        firstLagAt = 0;
                }if (shutdownRequested)
                    return false;

                if (wakeUpAt > now)
                    LockSupport.parkNanos(wakeUpAt - now);
            } catch (Throwable t) {
                if (!CommitLog.handleCommitError("Failed to persist commits to disk", t))
                    return false;
                LockSupport.parkNanos(markerIntervalNanos);
            }
            return true;
        }

        @VisibleForTesting
        boolean maybeLogFlushLag(long pollStarted, long now) {
            long flushDuration = now - pollStarted;
            totalSyncDuration += flushDuration;
            long maxFlushTimestamp = pollStarted + syncIntervalMillis;
            if (maxFlushTimestamp > now)
                return false;
            if (firstLagAt == 0) {
                firstLagAt = now;
                syncExceededIntervalBy = lagCount = 0;
                syncCount = 1;
                totalSyncDuration = flushDuration;
            }
            syncExceededIntervalBy += now - maxFlushTimestamp;
            lagCount++;
            if (firstLagAt > 0) {
                boolean logged = NoSpamLogger.log(logger, NoSpamLogger.Level.WARN, 5, TimeUnit.MINUTES, "Out of {} commit log syncs over the past {}s with average duration of {}ms, {} have exceeded the configured commit interval by an average of {}ms", syncCount, (now - firstLagAt) / 1000, String.format("%.2f", (double) totalSyncDuration / syncCount), lagCount, String.format("%.2f", (double) syncExceededIntervalBy / lagCount));
                if (logged)
                    firstLagAt = 0;
            }
            return true;
        }

        @VisibleForTesting
        long getTotalSyncDuration() {
            return totalSyncDuration;
        }
    }

    public void finishWriteFor(Allocation alloc) {
        maybeWaitForSync(alloc);
        written.incrementAndGet();
    }

    protected abstract void maybeWaitForSync(Allocation alloc);

    void requestExtraSync() {
        syncRequested = true;
        LockSupport.unpark(thread);
    }

    public void shutdown() {
        shutdown = true;
        requestExtraSync();
    }

    public void awaitTermination() throws InterruptedException {
        thread.join();
    }

    public long getCompletedTasks() {
        return written.get();
    }

    public long getPendingTasks() {
        return pending.get();
    }

    void awaitSyncAt(long syncTime, Context context) {
        do {
            WaitQueue.Signal signal = context != null ? syncComplete.register(context) : syncComplete.register();
            if (lastSyncedAt < syncTime)
                signal.awaitUninterruptibly();
            else
                signal.cancel();
        } while (lastSyncedAt < syncTime);
    }

    public void syncBlocking() {
        long requestTime = System.nanoTime();
        requestExtraSync();
        awaitSyncAt(requestTime, null);
    }

    final long markerIntervalNanos;
}
