package com.sugarweb.uims.domain.role;

import com.sugarweb.uims.domain.menu.Menu;
import com.sugarweb.framework.orm.BooleanEnum;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * 角色
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Role {

	@Id
	private String id;

	private String roleName;

	private String roleCode;

	@ManyToMany
	private Collection<Menu> menus;

	@Enumerated(EnumType.STRING)
	private BooleanEnum enable;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Role role = (Role) o;
		return id != null && Objects.equals(id, role.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
