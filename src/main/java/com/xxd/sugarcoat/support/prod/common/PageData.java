package com.xxd.sugarcoat.support.prod.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-13
 */
@Getter
@Setter
public class PageData<T> {

    /**
     * 分页数据
     */
    private List<T> dataList;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 每页记录数
     */
    private long pageSize;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 当前页数
     */
    private long currentPage;

    public PageData() {
    }

    public PageData(List<T> dataList, long totalCount, long pageSize, long totalPage, long currentPage) {
        this.dataList = dataList;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public static <T> PageData<T> empty() {
        return new PageData<>(new ArrayList<>(), 0, 0, 0, 0);
    }
}
