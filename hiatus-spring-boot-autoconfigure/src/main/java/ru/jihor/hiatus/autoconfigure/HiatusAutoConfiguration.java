package ru.jihor.hiatus.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jihor.hiatus.ServiceStatus;
import ru.jihor.hiatus.UnitOfWorkCounter;
import ru.jihor.hiatus.advice.MethodUnitOfWorkInterceptor;
import ru.jihor.hiatus.bpp.HiatusBeanPostProcessor;
import ru.jihor.hiatus.endpoints.HiatusEndpoint;
import ru.jihor.hiatus.health.HiatusHealthIndicator;

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
    @ConditionalOnEnabledEndpoint(endpoint = HiatusEndpoint.class)
    public ServiceStatus serviceStatus() {
        return new ServiceStatus();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint(endpoint = HiatusEndpoint.class)
    public UnitOfWorkCounter counter() {
        return new UnitOfWorkCounter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint(endpoint = HiatusEndpoint.class)
    public MethodUnitOfWorkInterceptor methodUnitOfWorkInterceptor(UnitOfWorkCounter counter) {
        return new MethodUnitOfWorkInterceptor(counter);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint(endpoint = HiatusEndpoint.class)
    public HiatusBeanPostProcessor hiatusBeanPostProcessor(MethodUnitOfWorkInterceptor interceptor) {
        return new HiatusBeanPostProcessor(interceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public HiatusEndpoint hiatusEndpoint(ServiceStatus serviceStatus, UnitOfWorkCounter counter) {
        return new HiatusEndpoint(serviceStatus, counter);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint(endpoint = HiatusEndpoint.class)
    public HiatusEndpoint.HiatusOnEndpoint hiatusOnEndpoint(HiatusEndpoint hiatusEndpoint) {
        return hiatusEndpoint.getHiatusOnEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint(endpoint = HiatusEndpoint.class)
    public HiatusEndpoint.HiatusOffEndpoint hiatusOffEndpoint(HiatusEndpoint hiatusEndpoint) {
        return hiatusEndpoint.getHiatusOffEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledHealthIndicator("hiatus")
    @ConditionalOnEnabledEndpoint(endpoint = HiatusEndpoint.class)
    public HiatusHealthIndicator hiatusHealthIndicator(HiatusEndpoint hiatusEndpoint) {
        return new HiatusHealthIndicator(hiatusEndpoint);
    }
}
