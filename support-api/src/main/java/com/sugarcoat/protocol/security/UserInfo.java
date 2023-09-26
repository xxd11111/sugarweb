package com.sugarcoat.protocol.security;

import lombok.Data;

/**
 * 用户信息
 *
 * @author xxd
 * @since 2023/8/11
 */
@Data
public class UserInfo {

    private String id;

    private String username;

    private String userType;

}
