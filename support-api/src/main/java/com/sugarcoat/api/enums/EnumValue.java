package com.sugarcoat.api.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * 枚举
 *
 * @author xxd
 * @date 2022-11-14
 */
public interface EnumValue<T extends Serializable> {

    @JsonValue
    T getCode();

}
