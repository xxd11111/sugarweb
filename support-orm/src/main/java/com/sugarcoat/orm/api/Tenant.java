package com.sugarcoat.orm.api;

/**
 * 租户注解
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/31
 */
public @interface Tenant {

    String columnName() default "";

}
