package com.xxd.sugarcoat.support.prod.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/25
 */
@Component
public class PageDataAdaptManager {

    private static PageDataAdapt pageDataAdapt;

    //@Autowired
    //public void setPageDataAdapt(PageDataAdapt pageDataAdapt) {
    //    PageDataAdaptManager.pageDataAdapt = pageDataAdapt;
    //}

    public static <T> PageData<T> convert(Object page, Class<T> clazz) {
        return pageDataAdapt.convert(page, clazz);
    }
}
