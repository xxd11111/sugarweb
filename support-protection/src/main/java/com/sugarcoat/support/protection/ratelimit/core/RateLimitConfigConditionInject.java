package com.sugarcoat.support.protection.ratelimit.core;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.exception.RateLimitException;
import com.sugarcoat.api.protection.EnableRateLimit;
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

    private static final String ERROR_MSG = "error enable rate limit config, because : ";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Assert.notNull(beanFactory, "error bean factory");
        String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(EnableRateLimit.class);
        if (ArrayUtil.isNotEmpty(beanNamesForAnnotation) && beanNamesForAnnotation.length != 1) {
            throw new RateLimitException(ERROR_MSG + "exist multiple @EnableRateLimit");
        }
        return true;
    }
}
