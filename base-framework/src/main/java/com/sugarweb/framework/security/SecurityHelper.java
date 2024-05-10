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
        return getAuthenticationInfo().getUserInfo();
    }

    public static AccessToken getTokenInfo() {
        return getAuthenticationInfo().getAccessToken();
    }

    public static AuthenticationInfo getAuthenticationInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AuthenticationInfo) authentication.getPrincipal();
    }


}
