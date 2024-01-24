package com.sugarcoat.support.parameter.application;

import com.xxd.common.PageData;
import com.xxd.common.PageRequest;

import java.util.Set;

/**
 * 参数缓存
 *
 * @author xxd
 * @version 1.0
 */
public interface ParamService {

	void save(ParamDto paramDTO);

	ParamDto findByCode(String code);

	ParamDto findById(String id);

	PageData<ParamDto> findPage(PageRequest pageRequest, ParamQueryDto cmd);

	void reset(Set<String> ids);

}
