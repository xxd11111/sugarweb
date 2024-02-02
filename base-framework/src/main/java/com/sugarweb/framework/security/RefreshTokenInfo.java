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
public class RefreshTokenInfo {

    private String refreshToken;

    private LocalDateTime expireTime;

    private String ip;

    private String userAgent;

    private String userId;

}
