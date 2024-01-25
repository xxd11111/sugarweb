package com.sugarweb.uims.application.vo;

import com.sugarweb.uims.domain.user.AccountType;
import com.sugarweb.framework.orm.BooleanEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
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

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToMany
    private Set<RoleVo> roles;

    private BooleanEnum enable;
}
