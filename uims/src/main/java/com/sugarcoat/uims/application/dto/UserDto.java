package com.sugarcoat.uims.application.dto;

import com.sugarcoat.api.common.BooleanFlag;
import lombok.Data;

/**
 * 用户dto
 *
 * @author xxd
 * @date 2022-12-27
 */
@Data
public class UserDto {

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private String password;

    private BooleanFlag enable;
}
