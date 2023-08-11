package com.sugarcoat.support.param.application;

import com.sugarcoat.support.param.domain.SugarcoatParameter;

/**
 * 参数转换类
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/31
 */
public class SugarcoatParameterConvert {

	public static ParameterDTO getParamDTO(SugarcoatParameter sugarcoatParam) {
		ParameterDTO parameterDTO = new ParameterDTO();
		parameterDTO.setId(sugarcoatParam.getId());
		parameterDTO.setCode(sugarcoatParam.getCode());
		parameterDTO.setName(sugarcoatParam.getName());
		parameterDTO.setValue(sugarcoatParam.getValue());
		parameterDTO.setDefaultValue(sugarcoatParam.getDefaultValue());
		parameterDTO.setComment(sugarcoatParam.getComment());
		return parameterDTO;
	}

}
