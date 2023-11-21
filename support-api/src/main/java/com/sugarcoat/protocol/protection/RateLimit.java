package com.sugarcoat.protocol.protection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流注解
 *
 * @author xxd
 * @since 2022-11-21
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 限流标识
     * @return 限流标识
     */
    String mark();

    /**
     * 频率时间 默认：1
     * @return 频率时间
     */
    long rateCycle() default 1L;

    /**
     * 频次 默认：1
     * @return 时间范围
     */
    long rate() default 1L;

    /**
     * 超出限制提示
     * @return 超出限制提示
     */
    String errorMsg() default "次数调用完毕，请稍后重试";

}
