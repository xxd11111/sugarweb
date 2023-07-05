package com.sugarcoat.param.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;

import java.util.Set;

/**
 * 参数缓存
 *
 * @author xxd
 * @date 2022-11-18
 */
public interface ParameterService {

	void save(ParameterDTO parameterDTO);

	void remove(String id);

	void reset(String id);

	ParameterDTO findByCode(String code);

	ParameterDTO findById(String id);

	PageData<ParameterDTO> findPage(PageDto pageDto, ParamQueryCmd cmd);

	void remove(Set<String> ids);

	void reset(Set<String> ids);

}
