package com.sugarweb.framework.security;

/**
 * 授权服务
 *
 * @author 许向东
 * @version 1.0
 */
public interface AuthenticateService {

    void authenticate(String accessToken);

}
