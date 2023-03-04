package com.xxd.sugarcoat.support.dict;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-16
 */
@Getter
@Setter
public class SimpleDict implements Dict{
    private String type;
    private String code;
    private String name;
}
