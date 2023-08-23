package com.sugarcoat.support.protection.ratelimit;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;

/**
 * @Author lmh
 * @Description
 * @CreateTime 2023-08-23 17:09
 */

@Slf4j
public class RateLimitConfigConditionInject implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Assert.notNull(beanFactory, "error bean factory");
        String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(EnableRateLimit.class);
        return ArrayUtil.isNotEmpty(beanNamesForAnnotation);
    }
}
