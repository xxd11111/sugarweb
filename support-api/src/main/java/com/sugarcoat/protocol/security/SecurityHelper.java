package com.sugarcoat.protocol.security;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 * @since 2023/3/9
 */
public class SecurityHelper {

    private static final ThreadLocal<UserInfo> userInfo = new ThreadLocal<>();

    public static void setUserInfo(UserInfo userInfo){
        SecurityHelper.userInfo.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return userInfo.get();
    }

}
