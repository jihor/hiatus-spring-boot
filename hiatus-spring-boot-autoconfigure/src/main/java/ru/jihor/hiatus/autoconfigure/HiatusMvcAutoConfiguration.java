package ru.jihor.hiatus.autoconfigure;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jihor.hiatus.endpoints.HiatusEndpoint;
import ru.jihor.hiatus.endpoints.mvc.HiatusMvcEndpoint;
import ru.jihor.hiatus.endpoints.mvc.HiatusOffMvcEndpoint;
import ru.jihor.hiatus.endpoints.mvc.HiatusOnMvcEndpoint;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
@Configuration
@ConditionalOnClass(Endpoint.class)
@ConditionalOnWebApplication
@AutoConfigureAfter({ HiatusAutoConfiguration.class })
public class HiatusMvcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HiatusMvcEndpoint hiatusMvcEndpoint(HiatusEndpoint hiatusEndpoint) {
        return new HiatusMvcEndpoint(hiatusEndpoint);
    }

    @Bean
    @ConditionalOnMissingBean
    public HiatusOnMvcEndpoint hiatusOnMvcEndpoint(HiatusEndpoint.HiatusOnEndpoint hiatusOnEndpoint) {
        return new HiatusOnMvcEndpoint(hiatusOnEndpoint);
    }

    @Bean
    @ConditionalOnMissingBean
    public HiatusOffMvcEndpoint hiatusOffMvcEndpoint(HiatusEndpoint.HiatusOffEndpoint hiatusOffEndpoint) {
        return new HiatusOffMvcEndpoint(hiatusOffEndpoint);
    }

}
