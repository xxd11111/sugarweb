package com.sugarcoat.support.orm.datapermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限启用注解
 *
 * @author xxd
 * @since 2023/11/15 22:49
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPermissionFilter {

    String customStrategy() default "";

}
