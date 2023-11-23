package com.sugarcoat.support.param.application;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 参数dto
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/24
 */
@Data
public class ParamDto {

	@NotEmpty
	private String id;

	private String code;

	private String name;

	private String value;

	private String comment;

	private String defaultValue;

}
