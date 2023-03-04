package com.xxd.sugarcoat.extend.uims.domain.model.user;

import cn.hutool.core.collection.CollUtil;
import com.xxd.sugarcoat.extend.uims.domain.model.role.Role;
import com.xxd.sugarcoat.support.status.AccessUtil;
import com.xxd.sugarcoat.support.status.AccessibleEnum;
import com.xxd.sugarcoat.extend.uims.domain.model.api.Api;
import com.xxd.sugarcoat.support.account.Account;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxd
 * @description TODO
 * @date 2022-10-28
 */
@Data
public class User implements Account {

    private String id;

    private String username;

    private String password;

    private String salt;

    private String nickName;

    private String mobilePhone;

    private String email;

    private Set<Role> roles;

    private Set<Api> apis;

    private AccessibleEnum accessible;

    public Set<String> listRoleCodes() {
        if (CollUtil.isEmpty(roles)) {
            return new HashSet<>();
        }
        return roles.stream().map(Role::getRoleCode).collect(Collectors.toSet());
    }

    public Set<String> listApiCodes() {
        if (CollUtil.isEmpty(apis)) {
            return new HashSet<>();
        }
        return apis.stream().map(Api::getCode).collect(Collectors.toSet());
    }

    public boolean isAccess() {
        return AccessUtil.isAccess(accessible);
    }
}
