package com.sugarweb.server.aspect;

import java.lang.annotation.*;

/**
 * 接口记录
 *
 * @author xxd
 * @version 1.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

	String value();

}
