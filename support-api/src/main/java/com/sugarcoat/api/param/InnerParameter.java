package com.sugarcoat.api.param;

import java.lang.annotation.*;

/**
 * 系统内部参数注解
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerParameter {

    String code() default "";

    String value() default "";

}
