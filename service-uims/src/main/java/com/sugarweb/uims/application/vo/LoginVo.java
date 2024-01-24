package com.sugarweb.uims.application.vo;

import com.sugarweb.uims.domain.user.AccountType;
import lombok.Data;

import java.util.Set;

/**
 * 登录返回信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class LoginVo {

    private String userId;

    private String lastLoginIp;

    private Long lastLoginTime;

    private String lastLoginPlatform;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private AccountType accountType;

    private Set<LoginMenuVo> menus;

}
