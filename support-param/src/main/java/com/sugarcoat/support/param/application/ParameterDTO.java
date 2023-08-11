package com.sugarcoat.support.param.application;

import lombok.Data;

/**
 * 参数dto
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/24
 */
@Data
public class ParameterDTO {

	private String id;

	private String code;

	private String name;

	private String value;

	private String comment;

	private String defaultValue;

}
