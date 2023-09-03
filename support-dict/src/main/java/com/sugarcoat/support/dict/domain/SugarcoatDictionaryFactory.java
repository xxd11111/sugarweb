package com.sugarcoat.support.dict.domain;

import com.sugarcoat.support.dict.application.dto.DictionaryGroupDto;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典静态工厂
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/25
 */
public class SugarcoatDictionaryFactory {

	public static SugarcoatDictionaryGroup create(DictionaryGroupDto dictionaryGroupDTO) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup;
		sugarcoatDictionaryGroup = new SugarcoatDictionaryGroup();
		sugarcoatDictionaryGroup.setGroupId(dictionaryGroupDTO.getId());
		sugarcoatDictionaryGroup.setGroupCode(dictionaryGroupDTO.getGroupCode());
		sugarcoatDictionaryGroup.setGroupName(dictionaryGroupDTO.getGroupName());
		List<DictionaryDto> dictionaryDtoList = dictionaryGroupDTO.getDictionaryDtoList();
		ArrayList<SugarcoatDictionary> sugarcoatDictionaryList = new ArrayList<>();
		for (DictionaryDto dictionaryDTO : dictionaryDtoList) {
			SugarcoatDictionary sugarcoatDictionary = new SugarcoatDictionary();
			sugarcoatDictionary.setDictionaryId(dictionaryDTO.getId());
			sugarcoatDictionary.setCode(dictionaryDTO.getCode());
			sugarcoatDictionary.setName(dictionaryDTO.getName());
			sugarcoatDictionaryList.add(sugarcoatDictionary);
		}
		sugarcoatDictionaryGroup.setSugarcoatDictionaries(sugarcoatDictionaryList);
		return sugarcoatDictionaryGroup;
	}

}
