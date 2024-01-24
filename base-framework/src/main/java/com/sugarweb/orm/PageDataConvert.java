package com.sugarweb.orm;

import com.sugarweb.common.PageData;
import com.sugarweb.common.PageDataAdapt;
import com.sugarweb.exception.FrameworkException;
import org.springframework.data.domain.Page;

/**
 * 分页适配器管理者
 *
 * @author xxd
 * @version 1.0
 */
public class PageDataConvert {

    private static final PageDataAdapt pageDataAdapt = new PageDataAdapt() {
        @Override
        public <T> PageData<T> convert(Object t, Class<T> clazz) {
            if (t instanceof Page page) {
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
