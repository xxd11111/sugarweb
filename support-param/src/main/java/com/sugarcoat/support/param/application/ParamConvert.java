package com.sugarcoat.support.param.application;

import com.sugarcoat.support.param.domain.SugarcoatParameter;

/**
 * 参数转换类
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
public class ParamConvert {

	public static ParamDto getParamDTO(SugarcoatParameter sugarcoatParam) {
		ParamDto paramDTO = new ParamDto();
		paramDTO.setId(sugarcoatParam.getId());
		paramDTO.setCode(sugarcoatParam.getCode());
		paramDTO.setName(sugarcoatParam.getName());
		paramDTO.setValue(sugarcoatParam.getValue());
		paramDTO.setDefaultValue(sugarcoatParam.getDefaultValue());
		paramDTO.setComment(sugarcoatParam.getComment());
		return paramDTO;
	}

}
