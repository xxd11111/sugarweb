package com.sugarcoat.support.dict.domain;

import com.sugarcoat.support.dict.application.DictionaryGroupDTO;
import com.sugarcoat.support.dict.application.DictionaryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典静态工厂
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/25
 */
public class SugarcoatDictionaryFactory {

	public static SugarcoatDictionaryGroup create(DictionaryGroupDTO dictionaryGroupDTO) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup;
		sugarcoatDictionaryGroup = new SugarcoatDictionaryGroup();
		sugarcoatDictionaryGroup.setGroupId(dictionaryGroupDTO.getId());
		sugarcoatDictionaryGroup.setGroupCode(dictionaryGroupDTO.getGroupCode());
		sugarcoatDictionaryGroup.setGroupName(dictionaryGroupDTO.getGroupName());
		List<DictionaryDTO> dictionaryDTOList = dictionaryGroupDTO.getDictionaryDTOList();
		ArrayList<SugarcoatDictionary> sugarcoatDictionaryList = new ArrayList<>();
		for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
			SugarcoatDictionary sugarcoatDictionary = new SugarcoatDictionary();
			sugarcoatDictionary.setDictionaryId(dictionaryDTO.getId());
			sugarcoatDictionary.setDictionaryCode(dictionaryDTO.getDictionaryCode());
			sugarcoatDictionary.setDictionaryName(dictionaryDTO.getDictionaryName());
			sugarcoatDictionaryList.add(sugarcoatDictionary);
		}
		sugarcoatDictionaryGroup.setDictionaries(sugarcoatDictionaryList);
		return sugarcoatDictionaryGroup;
	}

}
