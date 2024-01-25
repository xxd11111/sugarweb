package com.sugarweb.server.application.dto;

import lombok.Data;

/**
 * SgcApiQueryDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ApiInfoQueryDto {

	private String code;

	private String name;

	private String url;

	private String methodType;

	private String status;

}
