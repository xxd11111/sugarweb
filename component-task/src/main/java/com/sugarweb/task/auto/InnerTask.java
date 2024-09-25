package com.sugarweb.task.auto;

import java.lang.annotation.*;

/**
 * InnerTaskBean
 *
 * @author 许向东
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerTask {

    String taskCode();

    String taskName() default "";

    boolean enabled() default true;

}
