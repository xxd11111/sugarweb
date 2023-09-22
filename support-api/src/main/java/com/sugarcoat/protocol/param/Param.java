package com.sugarcoat.protocol.param;

/**
 * 参数
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
public interface Param {

	/**
	 * 获取参数编码
	 * @return 参数编码
	 */
	String getCode();

	/**
	 * 获取参数值
	 * @return 参数值
	 */
	String getValue();

}
