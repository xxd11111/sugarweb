package com.sugarweb.uims.dto;

import com.sugarweb.framework.common.Flag;
import lombok.Data;

/**
 * UserUpdateDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserUpdateDto {
    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private String password;

    private Flag enable;
}
