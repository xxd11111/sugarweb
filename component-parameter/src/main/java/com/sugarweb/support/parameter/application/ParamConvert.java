package com.sugarweb.support.parameter.application;

import com.sugarweb.support.parameter.domain.SugarcoatParameter;

/**
 * 参数转换类
 *
 * @author xxd
 * @version 1.0
 */
public class ParamConvert {

	public static ParamDto getParamDTO(SugarcoatParameter sugarcoatParam) {
		ParamDto paramDTO = new ParamDto();
		paramDTO.setId(sugarcoatParam.getId());
		paramDTO.setCode(sugarcoatParam.getCode());
		paramDTO.setName(sugarcoatParam.getName());
		paramDTO.setValue(sugarcoatParam.getValue());
		paramDTO.setComment(sugarcoatParam.getComment());
		return paramDTO;
	}

}
