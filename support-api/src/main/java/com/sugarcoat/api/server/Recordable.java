package com.sugarcoat.api.server;

/**
 * 日志记录
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/1
 */
public @interface Recordable {

	String group();

	String title();

}
