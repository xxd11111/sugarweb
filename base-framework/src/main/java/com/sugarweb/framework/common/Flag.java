package com.sugarweb.framework.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标识枚举
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum Flag {

    /**
     * 否
     */
    FALSE("0"),
    /**
     * 是
     */
    TRUE("1");

    private final String code;

}
