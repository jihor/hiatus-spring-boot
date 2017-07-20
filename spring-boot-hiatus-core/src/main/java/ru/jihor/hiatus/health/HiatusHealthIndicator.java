package ru.jihor.hiatus.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import ru.jihor.hiatus.endpoints.HiatusEndpoint;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
public class HiatusHealthIndicator extends AbstractHealthIndicator {

    private final HiatusEndpoint hiatusEndpoint;

    public HiatusHealthIndicator(HiatusEndpoint hiatusEndpoint) {
        this.hiatusEndpoint = hiatusEndpoint;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (hiatusEndpoint.isPaused()) {
            builder.outOfService();
        } else {
            builder.up();
        }
        builder.withDetail(HiatusEndpoint.FIELD_NAME_PAUSED, hiatusEndpoint.isPaused())
                .withDetail(HiatusEndpoint.FIELD_NAME_COUNT, hiatusEndpoint.getCount());
    }
}
