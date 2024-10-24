package com.sugarweb.framework.common;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 分页查询条件
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PageQuery {

    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNumber = 1;

    @Min(value = 1, message = "每页条数最小值为 1")
    private Integer pageSize = 10;


    public int startIndex() {
        return (pageNumber - 1) * pageSize + 1;
    }

    public int endIndex() {
        return pageNumber * pageSize;
    }

}
