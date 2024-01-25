package com.sugarweb.support.dict.application;

import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.support.dict.application.dto.DictionaryQueryDto;
import com.sugarweb.support.dict.application.dto.DictionaryDto;
import com.sugarweb.support.dict.domain.Dictionary;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * 字典对外服务
 *
 * @author xxd
 * @version 1.0
 */
public interface DictionaryService {

	void save(DictionaryDto dto);

	void remove(Set<String> ids);

	void removeGroup(String group);

	DictionaryDto findOne(String id);

	PageData<DictionaryDto> findPage(PageRequest pageRequest, DictionaryQueryDto queryDto);

	void put(Dictionary dictionary);

	void put(Collection<Dictionary> dictionaries);

	/**
	 * 删除字典项
	 *
	 * @param group 字典组编码
	 * @param code  字典编码
	 */
	void remove(String group, String code);

	void removeAll();

	/**
	 * 删除字典组
	 *
	 * @param group 字典组编码
	 */
	void removeByGroup(String group);

	void removeById(String id);

	void removeByIds(Collection<String> ids);

	/**
	 * 获取字典
	 *
	 * @param group 字典组编码
	 * @param code  字典编码
	 * @return 字典
	 */
	Optional<Dictionary> get(String group, String code);

	Collection<Dictionary> getByGroup(String group);

	Optional<Dictionary> getById(String group);

	/**
	 * 获取所有字典
	 *
	 * @return 字典集合
	 */
	Collection<Dictionary> getAll();

	/**
	 * 获取字典名称
	 *
	 * @param code 字典组编码
	 * @param name 字典编码
	 * @return 字典名称
	 */
	Optional<Dictionary> getByName(String code, String name);

	/**
	 * 是否存在该字典
	 *
	 * @param group 字典组编码
	 * @param code  字典编码
	 * @return 是否
	 */
	boolean exist(String group, String code);

	boolean exist(String group);

}
