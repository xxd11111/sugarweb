package com.sugarcoat.uims.application.dto;

import com.sugarcoat.protocol.orm.BooleanEnum;
import lombok.Data;

/**
 * 用户dto
 *
 * @author xxd
 * @since 2022-12-27
 */
@Data
public class UserDto {

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private String password;

    private BooleanEnum enable;
}
