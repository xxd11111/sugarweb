package com.sugarcoat.protocol.orm;

/**
 * todo 租户注解
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
public @interface Tenant {

	String columnName() default "";

}
