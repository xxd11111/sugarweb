package com.sugarweb.uims.domain.user;

import com.sugarweb.framework.common.EnumValue;
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
public enum AccountType implements EnumValue<String> {

    SUPER_ADMIN("1", "超级管理员"),
    ADMIN("2", "管理员"),
    COMMON("3", "普通用户"),
    ANONYMOUS("4", "游客");

    private final String value;

    private final String desc;

}
