package com.sugarweb.uims.domain;

import com.sugarweb.framework.orm.BooleanEnum;
import com.sugarweb.uims.domain.constans.MenuType;
import com.sugarweb.uims.domain.constans.MenuUrlType;
import lombok.*;

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
public class Menu {

	/**
	 * 菜单id
	 */
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
	private MenuType menuType;

	/**
	 * url类型
	 */
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
	private BooleanEnum enable;

}
