package ru.jihor.hiatus.endpoints.mvc;

import org.springframework.boot.actuate.endpoint.mvc.AbstractEndpointMvcAdapter;
import ru.jihor.hiatus.annotations.ActuatorPostMapping;
import ru.jihor.hiatus.endpoints.HiatusEndpoint;

/**
 * MVC endpoint for requesting the service to go back to work
 *
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
public class HiatusOffMvcEndpoint extends AbstractEndpointMvcAdapter<HiatusEndpoint.HiatusOffEndpoint> {
    public HiatusOffMvcEndpoint(HiatusEndpoint.HiatusOffEndpoint delegate) {
        super(delegate);
    }

    @Override
    @ActuatorPostMapping
    protected Object invoke() {
        return super.invoke();
    }
}
