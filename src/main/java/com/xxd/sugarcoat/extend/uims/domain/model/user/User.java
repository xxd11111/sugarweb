package com.xxd.sugarcoat.extend.uims.domain.model.user;

import cn.hutool.core.collection.CollUtil;
import com.xxd.sugarcoat.extend.uims.domain.model.role.Role;
import com.xxd.sugarcoat.support.server.ServerApi;
import com.xxd.sugarcoat.support.status.AccessUtil;
import com.xxd.sugarcoat.support.status.AccessibleEnum;
import com.xxd.sugarcoat.support.user.api.UserInfo;
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
public class User implements UserInfo {

    private String id;

    private String username;

    private String password;

    private String salt;

    private AccountLevelEnum accountLevel;

    private String nickName;

    private String mobilePhone;

    private String email;

    private Set<Role> roles;

    private Set<ServerApi> serverApis;

    private AccessibleEnum accessible;

    public Set<String> listRoleCodes() {
        if (CollUtil.isEmpty(roles)) {
            return new HashSet<>();
        }
        return roles.stream().map(Role::getRoleCode).collect(Collectors.toSet());
    }

    public Set<String> listApiCodes() {
        if (CollUtil.isEmpty(serverApis)) {
            return new HashSet<>();
        }
        return serverApis.stream().map(ServerApi::getCode).collect(Collectors.toSet());
    }

    public boolean isAccess() {
        return AccessUtil.isAccess(accessible);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isSuperAdmin() {
        return false;
    }
}
