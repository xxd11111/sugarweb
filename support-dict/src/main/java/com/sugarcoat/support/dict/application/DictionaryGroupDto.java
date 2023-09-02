package com.sugarcoat.support.dict.application;

import lombok.Data;

import java.util.List;

/**
 * 字典组信息传输对象
 *
 * @author xxd
 * @since  2023/4/19 23:21
 */
@Data
public class DictionaryGroupDto {

	private String id;

	private String groupCode;

	private String groupName;

	private List<DictionaryDto> dictionaryDtoList;

}
