package com.sugarcoat.support.orm.datapermission;

/**
 * 数据权限启用注解
 *
 * @author xxd
 * @since 2023/11/15 22:49
 */
public @interface DataPermissionFilter {

    String customStrategy() default "";

}
