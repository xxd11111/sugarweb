package com.sugarweb.framework.orm;

/**
 * 标识枚举
 *
 * @author xxd
 * @version 1.0
 */
public enum BooleanEnum {

    /**
     * 否
     */
    FALSE("0"),
    /**
     * 是
     */
    TRUE("1");

    public String getValue() {
        return code;
    }

    BooleanEnum(String code) {
        this.code = code;
    }

    private final String code;

}
