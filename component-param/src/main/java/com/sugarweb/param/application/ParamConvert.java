package com.sugarweb.param.application;

import com.sugarweb.param.domain.Param;

/**
 * 参数转换类
 *
 * @author xxd
 * @version 1.0
 */
public class ParamConvert {

	public static ParamDto getParamDTO(Param param) {
		ParamDto paramDto = new ParamDto();
		paramDto.setId(param.getId());
		paramDto.setCode(param.getCode());
		paramDto.setName(param.getName());
		paramDto.setValue(param.getValue());
		paramDto.setComment(param.getComment());
		return paramDto;
	}

}
