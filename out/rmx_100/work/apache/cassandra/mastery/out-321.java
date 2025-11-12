package org.apache.cassandra.streaming;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cassandra.concurrent.NamedThreadFactory;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.utils.JVMStabilityInspector;
import org.apache.cassandra.io.sstable.SSTable;
import org.apache.cassandra.db.lifecycle.LifecycleNewTracker;
import org.apache.cassandra.schema.TableId;
import com.google.common.base.Preconditions;

public class StreamReceiveTask extends StreamTask {

    private static final Logger logger = LoggerFactory.getLogger(StreamReceiveTask.class);

    private static final ExecutorService executor = Executors.newCachedThreadPool(new NamedThreadFactory("StreamReceiveTask"));

    private final long totalSize;

    private volatile boolean done = false;

    public StreamReceiveTask(StreamSession session, TableId tableId, int totalStreams, long totalSize) {
        super(session, tableId);
        this.receiver = ColumnFamilyStore.getIfExists(tableId).getStreamManager().createStreamReceiver(session, totalStreams);
        this.totalStreams = totalStreams;
        this.totalSize = totalSize;
    }

    public synchronized void received(IncomingStream stream) {
        Preconditions.checkState(!session.isPreview(), "we should never receive sstables when previewing");
        if (done) {
            logger.warn("[{}] Received stream {} on already finished stream received task. Aborting stream.", session.planId(), stream.getName());
            receiver.discardStream(stream);
            return;
        }
        remoteStreamsReceived++;
        bytesReceived += stream.getSize();
        Preconditions.checkArgument(tableId.equals(stream.getTableId()));
        logger.debug("received {} of {} total files {} of total bytes {}", remoteStreamsReceived, totalStreams, bytesReceived, totalSize);
        receiver.received(stream);
        if (remoteStreamsReceived == totalStreams) {
            done = true;
            executor.submit(new OnCompletionRunnable(this));
        }
    }

    public int getTotalNumberOfFiles() {
        return totalStreams;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public synchronized 
<<<<<<< commits-rmx_100/apache/cassandra/4dd7faa75210f635af36c0852e9b0d2e8bdbb95c/StreamReceiveTask-0a96f4c.java
StreamReceiver
=======
LifecycleNewTracker
>>>>>>> commits-rmx_100/apache/cassandra/732c43b0f2dbc085c6e1da6498966fd656f32c2f/StreamReceiveTask-5388dd6.java
 
<<<<<<< commits-rmx_100/apache/cassandra/4dd7faa75210f635af36c0852e9b0d2e8bdbb95c/StreamReceiveTask-0a96f4c.java
getReceiver
=======
createLifecycleNewTracker
>>>>>>> commits-rmx_100/apache/cassandra/732c43b0f2dbc085c6e1da6498966fd656f32c2f/StreamReceiveTask-5388dd6.java
() {
        if (done)
            throw new RuntimeException(String.format("Stream receive task %s of cf %s already finished.", session.planId(), tableId));
        
<<<<<<< commits-rmx_100/apache/cassandra/4dd7faa75210f635af36c0852e9b0d2e8bdbb95c/StreamReceiveTask-0a96f4c.java
return receiver;
=======
return new LifecycleNewTracker() {

            @Override
            public void trackNew(SSTable table) {
                synchronized (StreamReceiveTask.this) {
                    txn.trackNew(table);
                }
            }

            @Override
            public void untrackNew(SSTable table) {
                synchronized (StreamReceiveTask.this) {
                    txn.untrackNew(table);
                }
            }

            public OperationType opType() {
                return txn.opType();
            }
        };
>>>>>>> commits-rmx_100/apache/cassandra/732c43b0f2dbc085c6e1da6498966fd656f32c2f/StreamReceiveTask-5388dd6.java

    }

    private static class OnCompletionRunnable implements Runnable {

        private final StreamReceiveTask task;

        public OnCompletionRunnable(StreamReceiveTask task) {
            this.task = task;
        }

        public void run() {
            try {
                if (ColumnFamilyStore.getIfExists(task.tableId) == null) {
                    task.receiver.abort();
                    task.session.taskCompleted(task);
                    return;
                }
                task.receiver.finished();
                ;
                task.session.taskCompleted(task);
            } catch (Throwable t) {
                JVMStabilityInspector.inspectThrowable(t);
                task.session.onError(t);
            } finally {
                task.receiver.cleanup();
            }
        }
    }

    public synchronized void abort() {
        if (done)
            return;
        done = true;
        receiver.abort();
    }

    private final int totalStreams;

    private int remoteStreamsReceived = 0;

    private final StreamReceiver receiver;

    private long bytesReceived = 0;
}
