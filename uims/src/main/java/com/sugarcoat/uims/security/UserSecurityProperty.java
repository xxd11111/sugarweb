package com.sugarcoat.uims.security;

import java.util.List;

/**
 * todo 用户安全配置
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/25
 */
public class UserSecurityProperty {
    /**
     * ip限制
     */
    private int accountIpLimit = 0;

    private List<String> allowIps;
}
