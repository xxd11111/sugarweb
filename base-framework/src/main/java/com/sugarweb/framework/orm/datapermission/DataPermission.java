package com.sugarweb.framework.orm.datapermission;

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
 * @version 1.0
 */
@ValueGenerationType(generatedBy = DataPermissionGeneration.class)
@AttributeBinderType(binder = DataPermissionBinder.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface DataPermission {
}
