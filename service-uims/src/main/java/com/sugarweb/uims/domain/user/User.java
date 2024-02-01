package com.sugarweb.uims.domain.user;

import com.google.common.collect.Iterables;
import com.sugarweb.framework.security.UserInfo;
import com.sugarweb.uims.domain.menu.Menu;
import com.sugarweb.uims.domain.role.Role;
import com.sugarweb.framework.orm.BooleanEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class User {

    @Id
    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private String salt;

    private String password;

    @ManyToMany
    @ToString.Exclude
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private BooleanEnum enable;

    public List<String> listRoles() {
        if (Iterables.isEmpty(roles)) {
            return new ArrayList<>();
        }
        return roles.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());
    }

    public List<Menu> listMenus() {
        return roles.stream()
                .map(Role::getMenus)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
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

    public void modifyPassword(String newPassword) {
        // todo 加密方式问题
        password = newPassword;
    }

    public boolean isAdmin() {
        return false;
    }

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
