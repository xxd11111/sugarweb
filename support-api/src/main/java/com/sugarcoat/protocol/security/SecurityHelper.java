package com.sugarcoat.protocol.security;

import com.sugarcoat.protocol.BeanUtil;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 * @since 2023/3/9
 */
public class SecurityHelper {

    private static final UserHolder userHolder = BeanUtil.getBean(UserHolder.class);

    public static UserInfo getUserInfo() {
        return userHolder.getUserInfo();
    }

}
