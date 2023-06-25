package com.sugarcoat.uims.domain.security;

import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/25
 */
public class UserSecurityProperty {
    /**
     * ip限制
     */
    private int accountIpLimit = 0;

    private List<String> allowIps;
}
