package ru.jihor.hiatus.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import ru.jihor.hiatus.endpoints.HiatusEndpoint;

/**
 * Health indicator. This indicator is included in overall health check and will return 'OUT OF SERVICE' status if the service is gone on hiatus.
 *
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
@RequiredArgsConstructor
public class HiatusHealthIndicator extends AbstractHealthIndicator {

    private final HiatusEndpoint hiatusEndpoint;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        if (hiatusEndpoint.isPaused()) {
            builder.outOfService();
        } else {
            builder.up();
        }
        builder.withDetail(HiatusEndpoint.FIELD_NAME_PAUSED, hiatusEndpoint.isPaused())
                .withDetail(HiatusEndpoint.FIELD_NAME_COUNT, hiatusEndpoint.getCount());
    }
}
