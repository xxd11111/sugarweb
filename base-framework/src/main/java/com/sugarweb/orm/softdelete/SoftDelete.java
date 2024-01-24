package com.sugarweb.orm.softdelete;

import org.hibernate.annotations.AttributeBinderType;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * LogicDelete
 *
 * @author xxd
 * @version 1.0
 */
@ValueGenerationType(generatedBy = SoftDeleteGeneration.class)
@AttributeBinderType(binder = SoftDeleteBinder.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface SoftDelete {
}
