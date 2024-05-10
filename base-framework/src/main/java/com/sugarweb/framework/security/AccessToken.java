package com.sugarweb.framework.security;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 会话信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class AccessToken {

    private String accessToken;

    private LocalDateTime expireTime;

    private String refreshToken;

    private String ip;

    private String userAgent;

    private String userId;

}
