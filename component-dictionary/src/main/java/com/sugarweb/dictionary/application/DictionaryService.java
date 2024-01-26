package com.sugarweb.dictionary.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.dictionary.application.dto.DictionaryQueryDto;
import com.sugarweb.dictionary.application.dto.DictionaryDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 字典服务
 *
 * @author xxd
 * @version 1.0
 */
public interface DictionaryService {

	void save(DictionaryDto dto);

	void save(Collection<DictionaryDto> dtos);

	void removeById(String id);

	void removeByIds(Set<String> ids);

	/**
	 * 删除字典项
	 *
	 * @param group 字典组编码
	 * @param code  字典编码
	 */
	void removeByCode(String group, String code);

	/**
	 * 删除字典组
	 *
	 * @param group 字典组编码
	 */
	void removeByGroup(String group);

	void removeAll();

	Optional<DictionaryDto> findById(String id);

	List<DictionaryDto> findByIds(Set<String> ids);

	Optional<DictionaryDto> findByCode(String group, String code);

	List<DictionaryDto> findByGroup(String group);

	PageData<DictionaryDto> findPage(PageQuery pageQuery, DictionaryQueryDto queryDto);

	List<DictionaryDto> findAll();

	/**
	 * 是否存在该字典
	 *
	 * @param group 字典组编码
	 * @param code  字典编码
	 * @return 是否
	 */
	boolean existByCode(String group, String code);

	boolean existByGroup(String group);

}
