package com.xxd.sugarcoat.support.dev.user.utils;

import com.xxd.sugarcoat.support.dev.user.api.UserHandler;
import com.xxd.sugarcoat.support.dev.user.api.UserInfo;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/9
 */
public class UserUtil {
    protected static UserHandler userHandler;

    private UserUtil(){}

    public static UserInfo currentAccount() {
        return userHandler.currentUser();
    }

    public static boolean isAdmin() {
        return userHandler.currentUser().isAdmin();
    }

    public static boolean isSuperAdmin() {
        return userHandler.currentUser().isSuperAdmin();
    }
}
