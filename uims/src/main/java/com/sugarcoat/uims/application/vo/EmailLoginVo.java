package com.sugarcoat.uims.application.vo;

import lombok.Data;

/**
 * 邮箱登录
 *
 * @author xxd
 * @date 2023/6/26 21:53
 */
@Data
public class EmailLoginVo {

    private String email;

    private String captcha;

}
