package com.xxd.sugarcoat.support.dict.api;

import lombok.Data;

/**
 * @author xxd
 * @version 1.0
 * @description: 基础字典信息
 * @date 2023/3/20
 */
@Data
public class BaseDict {
    private String id;
    private String type;
    private String code;
    private String name;
}
