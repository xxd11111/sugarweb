package com.sugarweb.uims.domain.constans;

import lombok.RequiredArgsConstructor;

/**
 * 菜单url类型
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public enum MenuUrlType {

    NONE("0"),

    INNER("1"),

    OUTER("2");

    private final String code;
}
