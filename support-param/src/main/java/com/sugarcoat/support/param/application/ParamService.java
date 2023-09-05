package com.sugarcoat.support.param.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;

import java.util.Set;

/**
 * 参数缓存
 *
 * @author xxd
 * @since 2022-11-18
 */
public interface ParamService {

	void save(ParamDto paramDTO);

	ParamDto findByCode(String code);

	ParamDto findById(String id);

	PageData<ParamDto> findPage(PageDto pageDto, ParamQueryDto cmd);

	void reset(Set<String> ids);

}
