package com.sugarcoat.uims.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxd
 * @description TODO
 * @date 2022-10-25
 */
@Getter
@AllArgsConstructor
public enum MenuType {

	DIR("1"), // 目录
	FUNC("2"); // 按钮

	/**
	 * 类型
	 */
	private final String code;

}
