package com.sugarcoat.protocol;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author xxd
 * @description 分页查询
 * @date 2022-11-13
 */
@Data
public class PageParam {

    private static final Integer PAGE_NUM = 1;
    private static final Integer PAGE_SIZE = 10;

    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum = PAGE_NUM;

    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = PAGE_SIZE;
}
