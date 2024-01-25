package com.sugarweb.uims.application.dto;

import com.sugarweb.framework.orm.BooleanEnum;
import lombok.Data;

/**
 * 用户dto
 *
 * @author xxd
 * @version 1.0
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
