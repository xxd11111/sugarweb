package com.xxd.sugarcoat.extend.uims.domain.model.user;

import cn.hutool.core.collection.CollUtil;
import com.xxd.sugarcoat.extend.uims.domain.model.role.Role;
import com.xxd.sugarcoat.support.server.ServerApi;
import com.xxd.sugarcoat.support.devUndo.status.AccessUtil;
import com.xxd.sugarcoat.support.devUndo.status.AccessibleEnum;
import com.xxd.sugarcoat.support.dev.user.api.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxd
 * @description TODO
 * @date 2022-10-28
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class User implements UserInfo {
    @Id
    private String id;

    private String username;

    private String password;

    private String salt;

    @Enumerated(EnumType.STRING)
    private AccountLevelEnum accountLevel;

    private String nickName;

    private String mobilePhone;

    private String email;

    @Transient
    private Set<Role> roles;

    @Transient
    private Set<ServerApi> serverApis;

    @Enumerated(EnumType.STRING)
    private AccessibleEnum status;

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
        return AccessUtil.isAccess(status);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isSuperAdmin() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
