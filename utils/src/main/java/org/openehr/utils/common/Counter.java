package org.openehr.utils.common;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    public static final Counter instance = new Counter();

    private AtomicInteger count = new AtomicInteger(0);

    public Integer getAndIncrement() {
        return count.getAndIncrement();
    }

    public Integer getAndDecrement() {
        return count.getAndDecrement();
    }

}
