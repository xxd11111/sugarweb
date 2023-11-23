package com.sugarcoat.support.dict.application;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.support.dict.application.dto.DictionaryQueryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;

import java.util.Set;

/**
 * 字典对外服务
 *
 * @author xxd
 * @since 2022-11-21
 */
public interface DictionaryService {

	void save(DictionaryDto dto);

	void remove(Set<String> ids);

	void removeGroup(String group);

	DictionaryDto findOne(String id);

	PageData<DictionaryDto> findPage(PageDto pageDto, DictionaryQueryDto queryDto);

}
