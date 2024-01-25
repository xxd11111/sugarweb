package com.sugarweb.uims.domain.menu;

import com.sugarweb.framework.common.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum MenuType implements EnumValue<String> {

    DIR("1"), // 目录

    FUNC("2"); // 功能

    /**
     * 类型
     */
    private final String value;

}
