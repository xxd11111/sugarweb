package com.sugarweb.uims.application.dto;

import com.sugarweb.uims.constans.AccountType;
import com.sugarweb.framework.common.Flag;
import lombok.Data;

/**
 * 用户分页vo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserPageVo {

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private AccountType accountType;

    private Flag enable;
}
