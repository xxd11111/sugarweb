package com.sugarcoat.protocol;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxd
 * @description 分页数据
 * @date 2022-11-13
 */
@Getter
@Setter
public class PageData<T> {

    /**
     * 分页数据
     */
    private List<T> records;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 每页记录数
     */
    private long pageSize;
    /**
     * 当前页数
     */
    private long currentPage;

    public PageData() {
    }

    public PageData(List<T> records, long total, long pageSize, long currentPage) {
        this.records = records;
        this.total = total;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public static <T> PageData<T> empty() {
        return new PageData<>(new ArrayList<>(), 0, 0, 0);
    }

    public static <T> PageData<T> of(Page<T> page) {
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }
}
