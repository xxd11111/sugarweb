package com.sugarcoat.protocol.common;


/**
 * 标识枚举
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/27
 */
public enum BooleanFlag implements Flag<String> {

    /**
     * 否
     */
    FALSE("0"),
    /**
     * 是
     */
    TRUE("1");

    @Override
    public String getCode() {
        return code;
    }

    BooleanFlag(String code) {
        this.code = code;
    }

    private final String code;

}
