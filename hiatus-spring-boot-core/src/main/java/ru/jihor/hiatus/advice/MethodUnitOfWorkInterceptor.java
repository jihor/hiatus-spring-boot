package ru.jihor.hiatus.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import ru.jihor.hiatus.UnitOfWorkCounter;

/**
 * Interceptor for keeping track of units of work in processing
 *
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
public class MethodUnitOfWorkInterceptor implements MethodInterceptor {

    private final UnitOfWorkCounter counter;

    public MethodUnitOfWorkInterceptor(UnitOfWorkCounter counter) {
        this.counter = counter;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        counter.increase();
        try {
            return invocation.proceed();
        } finally {
            counter.decrease();
        }
    }
}
