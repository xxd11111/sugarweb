package com.sugarweb.uims.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单url类型
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum MenuUrlType {

    NONE("0"),

    INNER("1"),

    OUTER("2");

    private final String value;
}
