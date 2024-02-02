package com.sugarweb.framework.security;

import lombok.Data;

import java.util.List;

/**
 * AuthenticationInfo
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class AuthenticationInfo {

    private UserInfo userInfo;

    private AccessTokenInfo accessTokenInfo;

    private List<String> authorities;

    private List<String> roles;

}
