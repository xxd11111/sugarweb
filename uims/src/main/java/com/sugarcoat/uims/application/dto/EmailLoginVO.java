package com.sugarcoat.uims.application.dto;

import lombok.Data;

/**
 * 邮箱登录
 *
 * @author xxd
 * @date 2023/6/26 21:53
 */
@Data
public class EmailLoginVO {

    private String email;

    private String captcha;

}
