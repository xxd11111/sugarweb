package com.sugarcoat.support.parameter.application;

import com.xxd.common.PageData;
import com.xxd.common.PageDto;

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
