package com.sugarcoat.api.protection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 幂等
 * @author xxd
 * @since 2022-11-21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 有效期 默认：1，有效期要大于程序执行时间，否则请求还是可能会进来
     * @return 有效期
     */
    long expire() default 1L;

    /**
     * 时间单位，默认：s
     * @return TimeUnit
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 提示信息，可自定义
     * @return 提示信息
     */
    String errorMsg() default "重复请求，请稍后重试";

}
