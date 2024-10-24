package com.sugarweb.framework.orm;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugarweb.framework.common.PageQuery;

/**
 * PageUtil
 *
 * @author xxd
 * @version 1.0
 */
public class PageHelper {

    public static <T> IPage<T> getPage(PageQuery pageQuery){
        return new Page<>(pageQuery.getPageNumber(), pageQuery.getPageSize());
    }
}
