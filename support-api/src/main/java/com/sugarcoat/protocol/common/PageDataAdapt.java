package com.sugarcoat.protocol.common;

/**
 * 分页结果适配器
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/25
 */
public interface PageDataAdapt {

	/**
	 * 转为pageData
	 * @param t 待转换目标
	 * @return 标准分页结果
	 */
	<T> PageData<T> convert(Object t, Class<T> clazz);

}
