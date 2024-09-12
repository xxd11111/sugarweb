package com.sugarweb.dictionary.application.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DictItemDto {

	@Size(max = 32)
	private String itemId;

	@Size(max = 32)
	private String groupId;

	@Size(max = 32)
	private String itemCode;

	@Size(max = 32)
	private String itemName;

	@Size(max = 1)
	private String itemStatus;

}