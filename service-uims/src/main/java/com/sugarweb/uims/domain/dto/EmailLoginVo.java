package com.sugarweb.uims.domain.dto;

import lombok.Data;

/**
 * 邮箱登录
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class EmailLoginVo {

    private String email;

    private String captcha;

}
