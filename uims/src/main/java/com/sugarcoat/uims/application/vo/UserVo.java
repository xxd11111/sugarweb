package com.sugarcoat.uims.application.vo;

import com.sugarcoat.api.common.BooleanFlag;
import com.sugarcoat.uims.domain.user.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

/**
 * 用户vo
 *
 * @author xxd
 * @date 2023/6/26 21:41
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

    private BooleanFlag enable;
}
