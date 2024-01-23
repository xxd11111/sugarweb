package com.sugarcoat.uims.application.dto;

import com.sugarcoat.uims.domain.user.AccountType;
import com.xxd.orm.BooleanEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * 用户查询dto
 *
 * @author xxd
 * @since 2023/6/26 23:15
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
