package com.sugarcoat.uims.domain.menu;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Collection;
import java.util.Objects;

/**
 * TODO
 *
 * @author xxd
 * @date 2022-12-07
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Menu {

	/**
	 * 菜单id
	 */
	@Id
	private String id;

	/**
	 * 菜单pid
	 */
	private String pid;

	/**
	 * 菜单搜索索引
	 */
	private String menuIndex;

	/**
	 * 菜单名
	 */
	private String menuName;

	/**
	 * 菜单编码
	 */
	private String menuCode;

	/**
	 * 菜单类型
	 */
	@Enumerated(EnumType.STRING)
	private MenuType menuType;

	/**
	 * 菜单url
	 */
	private String menuUrl;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 菜单顺序
	 */
	private Integer orderNum;

	/**
	 * 菜单权限
	 */
	@Transient
	private Collection<String> serverApis;

	/**
	 * 状态
	 */
	private String status;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Menu menu = (Menu) o;
		return id != null && Objects.equals(id, menu.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
