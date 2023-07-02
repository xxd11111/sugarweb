package com.sugarcoat.uims.application.dto;

import lombok.Data;

/**
 * 密码
 *
 * @author xxd
 * @date 2023/6/26 21:44
 */
@Data
public class NewPasswordDto {

    private String oldPassword;

    private String newPassword;

    private String verifyUuid;

    private String verifyValue;
}
