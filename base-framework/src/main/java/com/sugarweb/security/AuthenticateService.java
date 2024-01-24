package com.sugarweb.security;

/**
 * 授权服务
 *
 * @author 许向东
 * @version 1.0
 */
public interface AuthenticateService {

    TokenInfo authenticate(String accessToken);

}
