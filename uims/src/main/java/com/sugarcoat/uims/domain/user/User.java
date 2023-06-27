package com.sugarcoat.uims.domain.user;

import cn.hutool.core.collection.CollUtil;
import com.sugarcoat.uims.domain.menu.Menu;
import com.sugarcoat.uims.domain.role.Role;
import com.sugarcoat.api.user.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author xxd
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

    private String mobilePhone;

    private String email;

    private String nickName;

    private String salt;

    private String password;

    private String userType;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToMany
    private Set<Role> roles;

    private String status;

    public Set<String> listRoles() {
        if (CollUtil.isEmpty(roles)) {
            return new HashSet<>();
        }
        return roles.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toSet());
    }

    public Set<Menu> listMenus() {
        return roles.stream()
                .map(Role::getMenus)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toSet());
    }

    public Set<String> listApiCodes() {
        return roles.stream()
                .map(Role::getMenus)
                .flatMap(Collection::parallelStream)
                .map(Menu::getApiCode)
                .collect(Collectors.toSet());
    }

    public void checkCertificate(String certificate) {
        // todo 加密方式问题
        boolean equals = certificate.equals(password);
        if (!equals) {
            throw new SecurityException("凭证错误");
        }
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
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
