package com.sugarcoat.support.scheduler.api;

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

    String id();

    String cron();

    String taskName() default "";

    String params() default "";

}
