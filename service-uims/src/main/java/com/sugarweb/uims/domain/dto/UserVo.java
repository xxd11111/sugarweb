package com.sugarweb.uims.domain.dto;

import com.sugarweb.uims.domain.constans.AccountType;
import com.sugarweb.framework.common.BooleanFlag;
import lombok.Data;

import java.util.Set;

/**
 * 用户vo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserVo {

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private AccountType accountType;

    private Set<RoleVo> roles;

    private BooleanFlag enable;
}
