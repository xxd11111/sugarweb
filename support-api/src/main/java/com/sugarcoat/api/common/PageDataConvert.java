package com.sugarcoat.api.common;

import org.springframework.stereotype.Component;

/**
 * 分页适配器管理者
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/25
 */
public class PageDataConvert {

	private static PageDataAdapt pageDataAdapt = new PageDataAdapt() {
		@Override
		public <T> PageData<T> convert(Object t, Class<T> clazz) {
			if (t instanceof Page){

			}
			return null;
		}
	};

	public static <T> PageData<T> convert(Object page, Class<T> clazz) {
		return pageDataAdapt.convert(page, clazz);
	}

}
