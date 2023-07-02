package com.sugarcoat.uims.application.dto;

import com.sugarcoat.api.common.BooleanFlag;
import com.sugarcoat.uims.domain.user.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * 用户分页vo
 *
 * @author xxd
 * @date 2022-12-28
 */
@Data
public class UserPageVo {

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BooleanFlag enable;
}
