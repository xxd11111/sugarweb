package com.sugarcoat.uims.domain.menu;

import lombok.RequiredArgsConstructor;

/**
 * 菜单url类型
 *
 * @author xxd
 * @date 2023/6/28 22:40
 */
@RequiredArgsConstructor
public enum MenuUrlType {

    NONE("0"),

    INNER("1"),

    OUTER("2");

    private final String code;
}
