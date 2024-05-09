package com.sugarweb.framework.common;

/**
 * 标识枚举
 *
 * @author xxd
 * @version 1.0
 */
public enum BooleanFlag {

    /**
     * 否
     */
    FALSE("0"),
    /**
     * 是
     */
    TRUE("1");

    public String getCode() {
        return code;
    }

    BooleanFlag(String code) {
        this.code = code;
    }

    private final String code;

}
