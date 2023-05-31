package com.sugarcoat.param.application;

import lombok.Data;

/**
 * 参数查询指令
 * @author xxd
 * @date 2023/5/6 23:45
 */
@Data
public class ParamQueryCmd {
    private String code;
    private String name;
}
