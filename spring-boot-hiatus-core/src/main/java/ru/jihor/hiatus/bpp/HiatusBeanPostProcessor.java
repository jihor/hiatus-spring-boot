package ru.jihor.hiatus.bpp;

import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import ru.jihor.hiatus.annotations.UnitOfWork;
import ru.jihor.hiatus.advice.MethodUnitOfWorkInterceptor;

/**
 * @author jihor (dmitriy_zhikharev@rgs.ru)
 *         Created on 2017-07-20
 */
public class HiatusBeanPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    private final MethodUnitOfWorkInterceptor interceptor;

    public HiatusBeanPostProcessor(MethodUnitOfWorkInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void afterPropertiesSet() {
        // see MethodValidationPostProcessor
        Pointcut pointcut = new AnnotationMatchingPointcut(null, UnitOfWork.class);
        this.advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
    }

}
