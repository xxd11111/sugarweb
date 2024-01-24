package com.sugarcoat.uims.application.dto;

import lombok.Data;

/**
 * 密码登录
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PasswordLoginDto {

    private String account;

    private String password;

    private String captchaUuid;

    private String captcha;

}
