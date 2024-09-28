package com.sugarweb.task.infra.auto;

import java.lang.annotation.*;

/**
 * InnerTaskMethod
 *
 * @author xxd
 * @version 1.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerTaskTrigger {

    String triggerCode();

    String triggerName() default "";

    String cron();

    boolean enabled() default true;

}
