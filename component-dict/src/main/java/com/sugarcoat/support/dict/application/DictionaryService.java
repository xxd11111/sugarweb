package com.sugarcoat.support.dict.application;

import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.sugarcoat.support.dict.application.dto.DictionaryQueryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;

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

}
