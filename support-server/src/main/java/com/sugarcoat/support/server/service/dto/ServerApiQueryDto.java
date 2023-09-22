package com.sugarcoat.support.server.service.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/8
 */
@Data
public class ServerApiQueryDto {

	private String code;

	private String name;

	private String url;

	private String methodType;

	private String status;

}
