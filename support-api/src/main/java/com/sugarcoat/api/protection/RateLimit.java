package com.sugarcoat.api.protection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * todo 限流注解
 *
 * @author xxd
 * @date 2022-11-21
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
    long frequencyTime() default 1L;

    /**
     * 频率单位 默认：s
     * @return 频率单位
     */
    TimeUnit frequencyTimeUnit() default TimeUnit.SECONDS;

    /**
     * 频次 默认：1
     * @return 时间范围
     */
    long frequency() default 1L;

    /**
     * 超出限制提示
     * @return 超出限制提示
     */
    String errorMsg() default "次数调用完毕，请稍后重试";

}
