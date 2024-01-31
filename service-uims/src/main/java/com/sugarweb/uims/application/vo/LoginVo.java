package com.sugarweb.uims.application.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录返回信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class LoginVo {

    private String userId;

    private String accessToken;

    private LocalDateTime accessTokenExpiresTime;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiresTime;

}
