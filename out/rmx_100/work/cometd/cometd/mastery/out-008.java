package org.cometd.javascript;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Latch {

    private final AtomicReference<CountDownLatch> latch = new AtomicReference<>();

    public Latch(int count) {
        reset(count);
    }

    public void reset(int count) {
        latch.set(new CountDownLatch(count));
    }

    public boolean await(long timeout) throws InterruptedException {
        return latch.get().await(timeout, TimeUnit.MILLISECONDS);
    }

    public void countDown() {
        latch.get().countDown();
    }

    public long getCount() {
        return latch.get().getCount();
    }
}
