package com.sugarweb.uims.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录返回信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class TokenVo {

    private String userId;

    private String accessToken;

    private LocalDateTime accessTokenExpiresTime;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiresTime;

}
