package com.sugarweb.uims.application.dto;

import lombok.Data;

/**
 * 密码
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class NewPasswordDto {

    private String oldPassword;

    private String newPassword;

    private String verifyUuid;

    private String verifyValue;
}
