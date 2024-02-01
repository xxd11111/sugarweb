package com.sugarweb.framework.security;

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
        return getTokenInfo().getUserInfo();
    }

    public static TokenInfo getTokenInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (TokenInfo) authentication.getPrincipal();
    }

}
