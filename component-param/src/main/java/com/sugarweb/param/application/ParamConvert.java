package com.sugarweb.param.application;

import com.sugarweb.param.domain.po.Param;

/**
 * 参数转换类
 *
 * @author xxd
 * @version 1.0
 */
public class ParamConvert {

	public static ParamDto toParamDto(Param param) {
		ParamDto paramDto = new ParamDto();
		paramDto.setParamId(param.getParamId());
		paramDto.setParamName(param.getParamName());
		paramDto.setParamCode(param.getParamCode());
		paramDto.setParamValue(param.getParamValue());
		paramDto.setParamComment(param.getParamComment());
		return paramDto;

	}

	public static Param toParam(ParamDto paramDto) {
		Param param = new Param();
		param.setParamId(paramDto.getParamId());
		param.setParamName(paramDto.getParamName());
		param.setParamCode(paramDto.getParamCode());
		param.setParamValue(paramDto.getParamValue());
		param.setParamComment(paramDto.getParamComment());
		return param;
	}

}
