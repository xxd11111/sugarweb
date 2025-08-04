package com.sugarweb.uims.dto;

import com.sugarweb.framework.common.Flag;
import lombok.Data;

/**
 * 用户dto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserSaveDto {

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private String password;

    private Flag enable;
}
