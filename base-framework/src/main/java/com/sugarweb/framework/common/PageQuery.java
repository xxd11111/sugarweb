package com.sugarweb.framework.common;

import jakarta.validation.constraints.Max;
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
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = 20;

}
