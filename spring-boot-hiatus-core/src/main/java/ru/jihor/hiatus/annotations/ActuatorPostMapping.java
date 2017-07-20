package ru.jihor.hiatus.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.actuate.endpoint.mvc.ActuatorMediaTypes;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This class is copied from spring-boot-actuator library
 *
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */

/**
 * Specialized {@link RequestMapping} for {@link RequestMethod#GET GET} requests that
 * produce {@code application/json} or
 * {@code application/vnd.spring-boot.actuator.v1+json} responses.
 *
 * @author Andy Wilkinson
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ResponseBody
@RequestMapping(method = RequestMethod.POST, produces = {
        ActuatorMediaTypes.APPLICATION_ACTUATOR_V1_JSON_VALUE,
        MediaType.APPLICATION_JSON_VALUE })
public @interface ActuatorPostMapping {

    /**
     * Alias for {@link RequestMapping#value}.
     * @return the value
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

}
