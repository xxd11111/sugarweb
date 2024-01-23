package com.xxd.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户持有者
 *
 * @author xxd
 * @version 1.0
 */
public class SugarcoatUserHolder implements UserHolder {

    @Override
    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfo)authentication.getPrincipal();
    }
}
