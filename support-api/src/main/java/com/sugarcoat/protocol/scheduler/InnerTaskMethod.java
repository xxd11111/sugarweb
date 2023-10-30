package com.sugarcoat.protocol.scheduler;

import java.lang.annotation.*;

/**
 * InnerTaskMethod
 *
 * @author 许向东
 * @date 2023/10/19
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerTaskMethod {

    String taskName() default "";

    String cron();

    String params() default "";

}