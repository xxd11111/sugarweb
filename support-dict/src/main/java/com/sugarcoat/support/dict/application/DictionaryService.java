package com.sugarcoat.support.dict.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.support.dict.application.dto.DictQueryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryGroupDto;

import java.util.Set;

/**
 * 字典对外服务
 *
 * @author xxd
 * @since 2022-11-21
 */
public interface DictionaryService {

	void save(DictionaryGroupDto dto);

	void save(DictionaryDto dto);

	void removeDictionaryGroup(Set<String> groupIds);

	void removeDictionary(Set<String> dictionaryIds);

	DictionaryDto findByDictionaryId(String dictionaryId);

	DictionaryGroupDto findByGroupId(String groupId);

	DictionaryGroupDto findByGroupCode(String groupCode);

	PageData<DictionaryGroupDto> findDictPage(PageDto pageDto, DictQueryDto queryDto);

}
