package com.xxd.orm.datapermission;

import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.annotation.Annotation;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/16
 */
public class DataPermissionFilterAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    /**
     * the advice
     */
    private final Advice advice;
    /**
     * the pointcut
     */
    private final Pointcut pointcut;

    /**
     * the annotation
     */
    private final Class<? extends Annotation> annotation;

    /**
     * 构造方法
     *
     * @param advice     切面
     * @param annotation 注解
     */
    public DataPermissionFilterAdvisor(@NonNull MethodInterceptor advice,
                                       @NonNull Class<? extends Annotation> annotation) {
        this.advice = advice;
        this.annotation = annotation;
        this.pointcut = buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    private Pointcut buildPointcut() {
        return new AnnotationMatchingPointcut(annotation, true);
    }


}
