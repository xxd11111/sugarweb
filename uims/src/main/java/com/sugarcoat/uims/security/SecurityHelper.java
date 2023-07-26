package com.sugarcoat.uims.security;

import com.sugarcoat.uims.domain.user.User;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 * @date 2023/3/9
 */
public class SecurityHelper {

    protected static ThreadLocal<User> currentUser = new ThreadLocal<>();

    private SecurityHelper() {
    }

    public static User currentAccount() {
        return currentUser.get();
    }

    public static String currentUserId() {
        return currentAccount().getId();
    }

    public static String currentSessionId() {
        //todo
        return null;
    }

    public static String getUsername() {
        return currentUser.get().getUsername();
    }

    public static String getUserType() {
        return currentUser.get().getUserType();
    }

    public static String getId() {
        return currentUser.get().getId();
    }

    public static boolean isAdmin() {
        return currentUser.get().isAdmin();
    }

    public static boolean isSuperAdmin() {
        return currentUser.get().isSuperAdmin();
    }

}
