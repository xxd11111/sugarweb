package com.sugarweb.param.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 参数缓存
 *
 * @author xxd
 * @version 1.0
 */
public interface ParamService {

	void save(ParamDto paramDto);

	void saveAll(Collection<ParamDto> paramDtos);

	void reset(Set<String> ids);

	void removeByCode(String code);

	void removeById(String id);

	void removeByIds(Set<String> ids);

	Optional<ParamDto> findByCode(String code);

	Optional<ParamDto> findById(String id);

	PageData<ParamDto> findPage(PageQuery pageQuery, ParamQueryDto cmd);

	List<ParamDto> findAll();

}
