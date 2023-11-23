package com.sugarcoat.protocol.parameter;

import java.lang.annotation.*;

/**
 * 系统内部参数注解
 * code为blank时，code为filed名称
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterCode {

    /**
     * 参数编码
     */
    String code() default "";

    /**
     * 参数名称
     */
    String name() default "";

    /**
     * 参数默认值
     */
    String value();

}
