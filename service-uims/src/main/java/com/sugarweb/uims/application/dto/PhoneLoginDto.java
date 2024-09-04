package com.sugarweb.uims.application.dto;

import lombok.Data;

/**
 * 手机登录
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PhoneLoginDto {

    private String mobilePhone;

    private String captcha;

}
