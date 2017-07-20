package ru.jihor.hiatus.endpoints.mvc;

import org.springframework.boot.actuate.endpoint.mvc.AbstractEndpointMvcAdapter;
import ru.jihor.hiatus.annotations.ActuatorGetMapping;
import ru.jihor.hiatus.endpoints.HiatusEndpoint;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
public class HiatusMvcEndpoint extends AbstractEndpointMvcAdapter<HiatusEndpoint> {
    public HiatusMvcEndpoint(HiatusEndpoint delegate) {
        super(delegate);
    }

    @Override
    @ActuatorGetMapping
    protected Object invoke() {
        return getDelegate().invoke();
//        if (!getDelegate().isEnabled()) {
//            // Shouldn't happen because the request mapping should not be registered
//            return getDisabledResponse();
//        }
//        Map<String, Object> endpointProps = this.getDelegate().invoke();
//        HttpStatus status = this.getDelegate().isPaused() ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.OK;
//        return new ResponseEntity<>(endpointProps, status);
    }
}
