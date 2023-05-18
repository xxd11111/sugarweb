package com.sugarcoat.uims.support.devUndo.status;

import com.sugarcoat.protocol.enums.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxd
 * @description 状态标识
 * @date 2022-11-12
 */
@Getter
@AllArgsConstructor
public enum AccessibleEnum implements EnumValue<Integer> {
    ENABLE(1, "启用"),
    DISABLE(0, "停用");

    private final Integer code;
    private final String desc;

}
