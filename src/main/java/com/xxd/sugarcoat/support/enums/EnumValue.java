package com.xxd.sugarcoat.support.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author xxd
 * @description 枚举值
 * @date 2022-11-14
 */
public interface EnumValue<T extends Serializable> {
    @JsonValue
    T getCode();
}
