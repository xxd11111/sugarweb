package com.sugarweb.uims.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号类型
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum AccountType {

    SUPER_ADMIN("1"),
    ADMIN("2"),
    COMMON("3"),
    ANONYMOUS("4");

    private final String value;

}
