package com.sugarcoat.support.scheduler;

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

    String cron() default "";

    boolean enable() default true;

    String[] params() default {};

}
