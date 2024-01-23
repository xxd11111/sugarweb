package com.xxd.server;

import java.lang.annotation.*;

/**
 * 接口记录
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/1
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

	/**
	 * 启用
	 */
	boolean enable() default true;

}
