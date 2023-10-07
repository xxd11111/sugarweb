package com.sugarcoat.protocol.server;

/**
 * 接口记录
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/1
 */
public @interface ApiLog {

	/**
	 * 启用
	 */
	boolean enable() default true;

}
