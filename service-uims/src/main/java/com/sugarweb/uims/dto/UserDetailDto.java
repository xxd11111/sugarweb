package com.sugarweb.uims.dto;

import com.sugarweb.uims.constans.AccountType;
import com.sugarweb.framework.common.Flag;
import lombok.Data;

import java.util.Set;

/**
 * 用户vo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserDetailDto {

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private AccountType accountType;

    private Set<RoleVo> roles;

    private Flag enable;
}
