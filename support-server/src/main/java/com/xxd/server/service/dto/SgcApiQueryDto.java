package com.xxd.server.service.dto;

import lombok.Data;

/**
 * SgcApiQueryDto
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/8
 */
@Data
public class SgcApiQueryDto {

	private String code;

	private String name;

	private String url;

	private String methodType;

	private String status;

}