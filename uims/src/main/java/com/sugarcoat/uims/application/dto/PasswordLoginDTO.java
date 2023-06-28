package com.sugarcoat.uims.application.dto;

import lombok.Data;

/**
 * 密码登录
 *
 * @author xxd
 * @date 2023/6/26 21:53
 */
@Data
public class PasswordLoginDTO {

    private String account;

    private String password;

    private String captchaUuid;

    private String captcha;

}
