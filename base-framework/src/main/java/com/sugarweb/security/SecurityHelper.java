package com.sugarweb.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 */
public class SecurityHelper {

    public static UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfo)authentication.getPrincipal();
    }

}
