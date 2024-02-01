package com.sugarweb.framework.security;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class TokenInfo {

    private String accessToken;

    private LocalDateTime accessTokenExpireTime;

    private String refreshToken;

    private LocalDateTime refreshExpireTime;

    private String ip;

    private String userAgent;

    private String userId;

    private UserInfo userInfo;

    private List<String> authorities;

    private List<String> roles;

}
