package com.sugarcoat.api.common;


/**
 * 标识枚举
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/27
 */
public enum BooleanFlag implements Flag<String> {

    /**
     * 是
     */
    TRUE("1"),
    /**
     * 否
     */
    FALSE("0");

    @Override
    public String getCode() {
        return code;
    }

    BooleanFlag(String code) {
        this.code = code;
    }

    private final String code;

}
