package com.sugarcoat.uims.domain.model.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxd
 * @description TODO
 * @date 2022-10-25
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    DIR("1"), // 目录
    MENU("2"), // 菜单
    BUTTON("3"); // 按钮

    /**
     * 类型
     */
    private final String code;
}
