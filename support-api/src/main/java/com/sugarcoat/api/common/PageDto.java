package com.sugarcoat.api.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * @author xxd
 * @description 分页查询
 * @date 2022-11-13
 */
@Data
public class PageDto {

    private static final Integer DEFAULT_PAGE = 1;

    private static final Integer DEFAULT_SIZE = 10;

    @Min(value = 1, message = "页码最小值为 1")
    private Integer page = DEFAULT_PAGE;

    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer size = DEFAULT_SIZE;


    public int getStart() {
        return (page - 1) * size + 1;
    }

    public int getEnd() {
        return page * size;
    }

}
