package com.sugarcoat.uims.application.dto;

import com.sugarcoat.uims.domain.user.AccountType;
import lombok.Data;

import java.util.Set;

/**
 * 登录返回信息
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
@Data
public class LoginVo {

    private String uuid;

    private String lastLoginIp;

    private Long lastLoginTime;

    private String lastLoginPlatform;

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private AccountType accountType;

    private Set<MenuTreeVo> menus;

}
