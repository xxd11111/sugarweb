package com.sugarweb.uims.application.dto;

import com.sugarweb.uims.domain.user.AccountType;
import com.sugarweb.framework.orm.BooleanEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * 用户查询dto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserQueryDto {

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BooleanEnum enable;
}
