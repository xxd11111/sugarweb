package com.sugarcoat.protocol.security;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 会话信息
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/26
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

}
