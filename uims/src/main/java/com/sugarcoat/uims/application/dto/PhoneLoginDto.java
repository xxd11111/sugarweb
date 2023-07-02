package com.sugarcoat.uims.application.dto;

import lombok.Data;

/**
 * 手机登录
 *
 * @author xxd
 * @date 2023/6/26 21:53
 */
@Data
public class PhoneLoginDto {

    private String mobilePhone;

    private String captcha;

}
