package ru.jihor.hiatus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method-level annotation denoting execution of the method as a unit of work
 *
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnitOfWork {
}
