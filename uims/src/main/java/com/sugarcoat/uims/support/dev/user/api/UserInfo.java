package com.sugarcoat.uims.support.dev.user.api;

/**
 * @author xxd
 * @description 账号
 * @date 2022-12-16
 */
public interface UserInfo {

    String getId();

    String getUsername();

    String getPassword();

    boolean isAdmin();

    boolean isSuperAdmin();
}
