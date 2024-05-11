package com.sugarweb.framework.security;

import com.sugarweb.framework.security.resource.UserInfo;
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
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication();
    }

}
