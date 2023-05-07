package com.xxd.sugarcoat.support.prod.param.api;

import lombok.Data;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/24
 */
@Data
public class ParamDTO {
    private String id;
    private String code;
    private String name;
    private String value;
    private String comment;
    private String defaultValue;
}
