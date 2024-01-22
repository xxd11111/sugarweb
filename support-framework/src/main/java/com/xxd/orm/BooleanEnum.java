package com.xxd.orm;

import com.sugarcoat.protocol.common.EnumValue;

/**
 * 标识枚举
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/27
 */
public enum BooleanEnum implements EnumValue<String> {

    /**
     * 否
     */
    FALSE("0"),
    /**
     * 是
     */
    TRUE("1");

    @Override
    public String getValue() {
        return code;
    }

    BooleanEnum(String code) {
        this.code = code;
    }

    private final String code;

    public static class Convert extends EnumConvert<BooleanEnum, String> {}

}
