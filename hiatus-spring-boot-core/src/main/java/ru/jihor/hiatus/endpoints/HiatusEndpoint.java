package ru.jihor.hiatus.endpoints;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.jihor.hiatus.ServiceStatus;
import ru.jihor.hiatus.UnitOfWorkCounter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic endpoint for providing information and changing status
 *
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
@Endpoint(id = "hiatus")
@RequiredArgsConstructor
public class HiatusEndpoint implements ApplicationContextAware {

    private final ServiceStatus serviceStatus;
    private final UnitOfWorkCounter counter;
    
    @Getter
    private final HiatusOnEndpoint hiatusOnEndpoint = new HiatusOnEndpoint();

    @Getter
    private final HiatusOffEndpoint hiatusOffEndpoint = new HiatusOffEndpoint();

    public static final String FIELD_NAME_PAUSED = "paused";
    public static final String FIELD_NAME_COUNT = "count";

    @ReadOperation
    public Object invoke() {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_NAME_PAUSED, isPaused());
        map.put(FIELD_NAME_COUNT, getCount());

        HttpStatus status = isPaused() ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.OK;

        return new ResponseEntity<>(Collections.unmodifiableMap(map), status);
    }

    public boolean isPaused() {
        return serviceStatus.isPaused();
    }

    public long getCount() {
        return counter.getCount();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {}

    @Endpoint(id = "hiatus-on")
    public class HiatusOnEndpoint {

        @WriteOperation
        public Boolean invoke() {
            return serviceStatus.pause();
        }
    }

    @Endpoint(id = "hiatus-off")
    public class HiatusOffEndpoint {

        @WriteOperation
        public Boolean invoke() {
            return serviceStatus.unpause();
        }
    }

}
