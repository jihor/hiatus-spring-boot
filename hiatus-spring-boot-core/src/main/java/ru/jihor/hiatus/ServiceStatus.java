package ru.jihor.hiatus;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for storing the service status. 'Paused' = 'on hiatus'
 *
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
public class ServiceStatus {
    private final AtomicBoolean paused = new AtomicBoolean(false);

    public boolean isPaused() {
        return paused.get();
    }

    public boolean pause() {
        return paused.compareAndSet(false, true);
    }

    public boolean unpause() {
        return paused.compareAndSet(true, false);

    }
}
