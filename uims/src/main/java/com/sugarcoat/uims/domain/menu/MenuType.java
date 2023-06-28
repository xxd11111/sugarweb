package com.sugarcoat.uims.domain.menu;

import com.sugarcoat.api.common.Flag;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author xxd
 * @date 2022-10-25
 */
@Getter
@AllArgsConstructor
public enum MenuType implements Flag {

    DIR("1"), // 目录

    FUNC("2"); // 功能

    /**
     * 类型
     */
    private final String code;

}
