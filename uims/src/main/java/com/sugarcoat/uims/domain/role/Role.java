package com.sugarcoat.uims.domain.role;

import com.sugarcoat.uims.domain.menu.Menu;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * 角色
 *
 * @author xxd
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

	@ManyToMany
	private Collection<Menu> menus;

	private String status;

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
