package com.sugarcoat.support.orm.tenant;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TenantDS
 *
 * @author xxd
 * @since 2023/11/2 22:36
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@DS("#tenantId")
public @interface TenantDS {
}
