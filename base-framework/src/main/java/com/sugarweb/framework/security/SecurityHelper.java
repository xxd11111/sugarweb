package com.sugarweb.framework.security;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 */
public class SecurityHelper {

    public static LoginUser getLoginUser() {
        return (LoginUser) StpUtil.getSession().get("userInfo");
    }

}
