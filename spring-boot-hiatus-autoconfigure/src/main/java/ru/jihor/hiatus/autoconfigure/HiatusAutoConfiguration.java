package ru.jihor.hiatus.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jihor.hiatus.endpoints.HiatusEndpoint;
import ru.jihor.hiatus.health.HiatusHealthIndicator;
import ru.jihor.hiatus.ServiceStatus;
import ru.jihor.hiatus.UnitOfWorkCounter;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
@Configuration
@ConditionalOnClass(Endpoint.class)
@AutoConfigureAfter({ EndpointAutoConfiguration.class })
public class HiatusAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ServiceStatus serviceStatus() {
        return new ServiceStatus();
    }

    @Bean
    @ConditionalOnMissingBean
    public UnitOfWorkCounter counter() {
        return new UnitOfWorkCounter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("endpoints.hiatus")
    public HiatusEndpoint hiatusEndpoint(ServiceStatus serviceStatus, UnitOfWorkCounter counter) {
        return new HiatusEndpoint(serviceStatus, counter);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("endpoints.hiatus-on")
    public HiatusEndpoint.HiatusOnEndpoint hiatusOnEndpoint(HiatusEndpoint hiatusEndpoint) {
        return hiatusEndpoint.getHiatusOnEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("endpoints.hiatus-off")
    public HiatusEndpoint.HiatusOffEndpoint hiatusOffEndpoint(HiatusEndpoint hiatusEndpoint) {
        return hiatusEndpoint.getHiatusOffEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledHealthIndicator("hiatus")
    public HiatusHealthIndicator hiatusHealthIndicator(HiatusEndpoint hiatusEndpoint) {
        return new HiatusHealthIndicator(hiatusEndpoint);
    }
}
