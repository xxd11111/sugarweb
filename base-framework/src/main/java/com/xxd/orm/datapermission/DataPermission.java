package com.xxd.orm.datapermission;

import org.hibernate.annotations.AttributeBinderType;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * DataPermission
 *
 * @author xxd
 * @since 2023/10/21 15:59
 */
@ValueGenerationType(generatedBy = DataPermissionGeneration.class)
@AttributeBinderType(binder = DataPermissionBinder.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface DataPermission {
}
