package com.sugarcoat.security;

/**
 * TODO
 *
 * @author 许向东
 * @date 2024/1/18
 */
public interface AuthenticateService {

    TokenInfo authenticate(String accessToken);

}
