package ru.jihor.hiatus.endpoints;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.jihor.hiatus.ServiceStatus;
import ru.jihor.hiatus.UnitOfWorkCounter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
public class HiatusEndpoint extends AbstractEndpoint<Map<String, Object>> implements ApplicationContextAware {

    private final ServiceStatus serviceStatus;
    private final UnitOfWorkCounter counter;
    
    @Getter
    private final HiatusOnEndpoint hiatusOnEndpoint = new HiatusOnEndpoint();

    @Getter
    private final HiatusOffEndpoint hiatusOffEndpoint = new HiatusOffEndpoint();

    public static final String FIELD_NAME_PAUSED = "paused";
    public static final String  FIELD_NAME_COUNT = "count";

    public HiatusEndpoint(ServiceStatus serviceStatus, UnitOfWorkCounter counter) {
        super("hiatus", false, true);
        this.serviceStatus = serviceStatus;
        this.counter = counter;
    }

    @Override
    public Map<String, Object> invoke() {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_NAME_PAUSED, isPaused());
        map.put(FIELD_NAME_COUNT, getCount());
        return Collections.unmodifiableMap(map);
    }

    public boolean isPaused() {
        return serviceStatus.isPaused();
    }

    public long getCount() {
        return counter.getCount();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    public class HiatusOnEndpoint extends AbstractEndpoint<Boolean> {

        public HiatusOnEndpoint() {
            super("hiatus_on", true, true);
        }

        @Override
        public Boolean invoke() {
            return serviceStatus.pause();
        }
    }

    public class HiatusOffEndpoint extends AbstractEndpoint<Boolean> {

        public HiatusOffEndpoint() {
            super("hiatus_off", true, true);
        }

        @Override
        public Boolean invoke() {
            return serviceStatus.unpause();
        }
    }

}
