package com.sugarcoat.uims.security;

import com.sugarcoat.protocol.security.UserHolder;
import com.sugarcoat.protocol.security.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户持有者
 *
 * @author xxd
 * @since 2023/8/11
 */
public class SugarcoatUserHolder implements UserHolder {

    @Override
    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfo)authentication.getPrincipal();
    }
}
