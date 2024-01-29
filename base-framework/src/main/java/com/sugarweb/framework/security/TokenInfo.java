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

    private LocalDateTime expireTime;

    private String refreshToken;

    private LocalDateTime refreshExpireTime;

    private String userId;

    private String ip;

    private String mac;

    private String platform;

    private String userAgent;

    private List<String> authorities;

    private List<String> roles;

}
