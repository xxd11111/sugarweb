package com.sugarcoat.uims.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/9
 */
@Getter
@AllArgsConstructor
public enum AccountType {

    SUPER_ADMIN(0, "超级管理员"),
    ADMIN(1, "管理员"),
    COMMON(2, "普通用户"),
    ANONYMOUS(3, "游客");

    private final Integer code;

    private final String desc;

}
