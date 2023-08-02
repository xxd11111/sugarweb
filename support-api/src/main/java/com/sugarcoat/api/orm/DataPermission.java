package com.sugarcoat.api.orm;

/**
 * todo 数据权限
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/31
 */
public @interface DataPermission {

	String columnName() default "";

	String type() default "";

}
