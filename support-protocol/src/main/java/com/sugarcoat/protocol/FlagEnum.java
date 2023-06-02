package com.sugarcoat.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标识枚举
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/27
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {
    /**
     * 是
     */
    TRUE("1"),
    /**
     * 否
     */
    FALSE("0");

    private final String code;
}
