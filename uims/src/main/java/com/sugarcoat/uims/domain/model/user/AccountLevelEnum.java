package com.sugarcoat.uims.domain.model.user;

import com.sugarcoat.api.enums.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/9
 */
@Getter
@AllArgsConstructor
public enum AccountLevelEnum implements EnumValue<Integer> {

	SUPER_ADMIN(0, "管理员"), ADMIN(1, "管理员"), USER(2, "用户"), ANONYMOUS(3, "游客");

	private final Integer code;

	private final String desc;

}