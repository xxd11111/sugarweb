package com.sugarcoat.security;

/**
 * 授权服务
 *
 * @author 许向东
 * @date 2024/1/18
 */
public interface AuthenticateService {

    TokenInfo authenticate(String accessToken);

}
