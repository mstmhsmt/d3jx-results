/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.streaming;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.base.Preconditions;

import org.apache.cassandra.db.lifecycle.LifecycleNewTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cassandra.concurrent.NamedThreadFactory;
import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.schema.TableId;
import org.apache.cassandra.io.sstable.SSTable;
import org.apache.cassandra.utils.JVMStabilityInspector;

/**
 * Task that manages receiving files for the session for certain ColumnFamily.
 */
public class StreamReceiveTask extends StreamTask
{
    private static final Logger logger = LoggerFactory.getLogger(StreamReceiveTask.class);

    private static final ExecutorService executor = Executors.newCachedThreadPool(new NamedThreadFactory("StreamReceiveTask"));

    private final StreamReceiver receiver;

    // number of streams to receive
    private final int totalStreams;

    // total size of streams to receive
    private final long totalSize;

    // true if task is done (either completed or aborted)
    private volatile boolean done = false;

    private int remoteStreamsReceived = 0;
    private long bytesReceived = 0;

    public StreamReceiveTask(StreamSession session, TableId tableId, int totalStreams, long totalSize)
    {
        super(session, tableId);
        this.receiver = ColumnFamilyStore.getIfExists(tableId).getStreamManager().createStreamReceiver(session, totalStreams);
        this.totalStreams = totalStreams;
        this.totalSize = totalSize;
    }

    /**
     * Process received stream.
     *
     * @param stream Stream received.
     */
    public synchronized void received(IncomingStream stream)
    {
        Preconditions.checkState(!session.isPreview(), "we should never receive sstables when previewing");

        if (done)
        {
            logger.warn("[{}] Received stream {} on already finished stream received task. Aborting stream.", session.planId(),
                        stream.getName());
            receiver.discardStream(stream);
            return;
        }

        remoteStreamsReceived++;
        bytesReceived += stream.getSize();
        Preconditions.checkArgument(tableId.equals(stream.getTableId()));
        logger.debug("received {} of {} total files {} of total bytes {}", remoteStreamsReceived, totalStreams,
                     bytesReceived, totalSize);

        receiver.received(stream);

        if (remoteStreamsReceived == totalStreams)
        {
            done = true;
            executor.submit(new OnCompletionRunnable(this));
        }
    }

    public int getTotalNumberOfFiles()
    {
        return totalStreams;
    }

    public long getTotalSize()
    {
        return totalSize;
    }

<<<<<<< commits-rmx_100/apache/cassandra/4dd7faa75210f635af36c0852e9b0d2e8bdbb95c/StreamReceiveTask-0a96f4c.java
    public synchronized StreamReceiver getReceiver()
||||||| commits-rmx_100/apache/cassandra/6a449b88d9ca7e6a73a9335c9983301f8e72bcff/StreamReceiveTask-f3966a7.java
    public synchronized LifecycleTransaction getTransaction()
=======
    /**
     * @return a LifecycleNewTracker whose operations are synchronised on this StreamReceiveTask.
     */
    public synchronized LifecycleNewTracker createLifecycleNewTracker()
>>>>>>> commits-rmx_100/apache/cassandra/732c43b0f2dbc085c6e1da6498966fd656f32c2f/StreamReceiveTask-5388dd6.java
    {
        if (done)
            throw new RuntimeException(String.format("Stream receive task %s of cf %s already finished.", session.planId(), tableId));
<<<<<<< commits-rmx_100/apache/cassandra/4dd7faa75210f635af36c0852e9b0d2e8bdbb95c/StreamReceiveTask-0a96f4c.java
        return receiver;
||||||| commits-rmx_100/apache/cassandra/6a449b88d9ca7e6a73a9335c9983301f8e72bcff/StreamReceiveTask-f3966a7.java
        return txn;
=======

        return new LifecycleNewTracker()
        {
            @Override
            public void trackNew(SSTable table)
            {
                synchronized (StreamReceiveTask.this)
                {
                    txn.trackNew(table);
                }
            }

            @Override
            public void untrackNew(SSTable table)
            {
                synchronized (StreamReceiveTask.this)
                {
                    txn.untrackNew(table);
                }
            }

            public OperationType opType()
            {
                return txn.opType();
>>>>>>> commits-rmx_100/apache/cassandra/732c43b0f2dbc085c6e1da6498966fd656f32c2f/StreamReceiveTask-5388dd6.java
            }
        };
    }

    private static class OnCompletionRunnable implements Runnable
    {
        private final StreamReceiveTask task;

        public OnCompletionRunnable(StreamReceiveTask task)
        {
            this.task = task;
        }

        public void run()
        {
            try
            {
                if (ColumnFamilyStore.getIfExists(task.tableId) == null)
                {
                    // schema was dropped during streaming
                    task.receiver.abort();
                    task.session.taskCompleted(task);
                    return;
                }

                task.receiver.finished();;
                task.session.taskCompleted(task);
            }
            catch (Throwable t)
            {
                JVMStabilityInspector.inspectThrowable(t);
                task.session.onError(t);
            }
            finally
            {
                task.receiver.cleanup();
            }
        }
    }

    /**
     * Abort this task.
     * If the task already received all files and
     * {@link org.apache.cassandra.streaming.StreamReceiveTask.OnCompletionRunnable} task is submitted,
     * then task cannot be aborted.
     */
    public synchronized void abort()
    {
        if (done)
            return;

        done = true;
        receiver.abort();
    }
}
