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
public class AccessTokenInfo {

    private String accessToken;

    private LocalDateTime expireTime;

    private String refreshToken;

    private String ip;

    private String userAgent;

    private String userId;

}
