package com.sugarweb.framework.security.base;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 */
public class SecurityHelper {

    public static UserInfo getUserInfo() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication();
    }

}
