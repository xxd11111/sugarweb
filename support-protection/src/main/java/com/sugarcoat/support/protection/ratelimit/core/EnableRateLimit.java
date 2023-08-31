package com.sugarcoat.support.protection.ratelimit.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 限流自动装配
 *
 * @author lmh
 * @Description
 * @CreateTime 2023-08-23 15:28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableRateLimit {

}
