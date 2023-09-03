package com.sugarcoat.api.security;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 * @since 2023/3/9
 */
public class SecurityHelper {

    private static UserHolder userHolder;

    public static void setUserHolder(UserHolder userHolder){
        SecurityHelper.userHolder = userHolder;
    }

    private SecurityHelper() {
    }

    public static UserInfo currentAccount() {
        return userHolder.getUserInfo();
    }

    public static String currentUserId() {
        return currentAccount().getId();
    }

    public static String currentSessionId() {
        return currentAccount().getSessionId();
    }

    public static String getUsername() {
        return currentAccount().getUsername();
    }

    public static String getUserType() {
        return currentAccount().getUserType();
    }

    public static String getId() {
        return currentAccount().getId();
    }

}
