package ru.jihor.hiatus;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
public class UnitOfWorkCounter {

    private final AtomicLong counter = new AtomicLong(0);

    public long getCount() {
        return counter.get();
    }

    public void increase() {
        counter.incrementAndGet();
    }

    public void decrease() {
        counter.decrementAndGet();
    }

}
