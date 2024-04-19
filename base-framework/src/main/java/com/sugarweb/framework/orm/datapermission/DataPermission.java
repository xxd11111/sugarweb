package com.sugarweb.framework.orm.datapermission;

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
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface DataPermission {
}
