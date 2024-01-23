package com.sugarcoat.uims.application.dto;

import lombok.Data;

/**
 * 密码登录
 *
 * @author xxd
 * @since 2023/6/26 21:53
 */
@Data
public class PasswordLoginDto {

    private String account;

    private String password;

    private String captchaUuid;

    private String captcha;

}
