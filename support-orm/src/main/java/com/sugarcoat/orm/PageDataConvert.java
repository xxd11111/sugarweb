package com.sugarcoat.orm;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDataAdapt;
import com.sugarcoat.api.exception.FrameworkException;
import org.springframework.data.domain.Page;

/**
 * 分页适配器管理者
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/25
 */
public class PageDataConvert {

    private static final PageDataAdapt pageDataAdapt = new PageDataAdapt() {
        @Override
        public <T> PageData<T> convert(Object t, Class<T> clazz) {
            if (t instanceof Page) {
                Page<T> page = (Page<T>) t;
                PageData<T> pageData = new PageData<>();
                pageData.setPage(page.getNumber());
                pageData.setContent(page.getContent());
                pageData.setTotal(page.getTotalElements());
                pageData.setSize(page.getSize());
				return pageData;
			}
			throw new FrameworkException("page类型不匹配");
        }
    };

    public static <T> PageData<T> convert(Object page, Class<T> clazz) {
        return pageDataAdapt.convert(page, clazz);
    }

}
