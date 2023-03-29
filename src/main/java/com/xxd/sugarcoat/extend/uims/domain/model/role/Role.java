package com.xxd.sugarcoat.extend.uims.domain.model.role;

import com.xxd.sugarcoat.extend.uims.domain.model.menu.Menu;
import com.xxd.sugarcoat.support.status.AccessibleEnum;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-07
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Role {
    @Id
    private Long id;

    private String roleName;

    private String roleCode;

    @Transient
    private Collection<Menu> menus;

    @Enumerated(EnumType.STRING)
    private AccessibleEnum status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
