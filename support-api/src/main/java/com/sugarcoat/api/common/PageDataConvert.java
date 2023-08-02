package com.sugarcoat.api.common;

import org.springframework.stereotype.Component;

/**
 * 分页适配器管理者
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/25
 */
@Component
public class PageDataConvert {

	private static PageDataAdapt pageDataAdapt;

	// @Autowired
	// public void setPageDataAdapt(PageDataAdapt pageDataAdapt) {
	// PageDataAdaptManager.pageDataAdapt = pageDataAdapt;
	// }

	public static <T> PageData<T> convert(Object page, Class<T> clazz) {
		return pageDataAdapt.convert(page, clazz);
	}

}
