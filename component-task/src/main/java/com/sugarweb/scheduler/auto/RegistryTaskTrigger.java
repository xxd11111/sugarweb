package com.sugarweb.scheduler.auto;

import java.lang.annotation.*;

/**
 * InnerTaskMethod
 *
 * @author 许向东
 * @version 1.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegistryTaskTrigger {

    String id();

    String cron();

    String taskName() default "";

    String params() default "";

}
