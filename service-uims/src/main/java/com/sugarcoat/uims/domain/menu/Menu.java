package com.sugarcoat.uims.domain.menu;

import com.xxd.orm.BooleanEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * 菜单
 *
 * @author xxd
 * @version 1.0
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
	 * 菜单编码
	 */
	private String menuCode;

	/**
	 * 菜单名
	 */
	private String menuName;

	/**
	 * 菜单类型
	 */
	@Enumerated(EnumType.STRING)
	private MenuType menuType;

	/**
	 * url类型
	 */
	@Enumerated(EnumType.STRING)
	private MenuUrlType menuUrlType;

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
	private Integer sequence;

	/**
	 * 菜单权限
	 */
	private String apiCode;

	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	private BooleanEnum enable;

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
