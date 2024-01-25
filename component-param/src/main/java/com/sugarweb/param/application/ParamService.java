package com.sugarweb.param.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;
import com.sugarweb.param.domain.Param;

import java.util.Collection;
import java.util.Optional;
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


	/**
	 * 获取参数值
	 *
	 * @param code 参数编码
	 * @return 参数值
	 */
	Optional<String> getValue(String code);

	/**
	 * 保存参数
	 *
	 * @param parameter 参数
	 */
	void save(Param parameter);

	/**
	 * 根据参数编码删除参数
	 *
	 * @param code 参数编码
	 */
	void remove(String code);

	/**
	 * 获取参数
	 *
	 * @param code 参数编码
	 * @return 参数
	 */
	Optional<Param> getParameter(String code);

	/**
	 * 获取全部系统参数
	 *
	 * @return 参数集合
	 */
	Collection<Param> getAll();

	/**
	 * 批量保存
	 *
	 * @param parameters 参数
	 */
	void batchSave(Collection<Param> parameters);
}
