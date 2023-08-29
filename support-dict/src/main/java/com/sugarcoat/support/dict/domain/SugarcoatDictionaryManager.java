package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.Dictionary;
import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.dict.DictionaryGroup;

import java.util.Optional;

/**
 * 字典客户端实现类
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/30
 */
public class SugarcoatDictionaryManager implements DictionaryManager {

	private final SugarcoatDictionaryGroupRepository dictionaryGroupRepository;

	public SugarcoatDictionaryManager(SugarcoatDictionaryGroupRepository dictionaryGroupRepository) {
		this.dictionaryGroupRepository = dictionaryGroupRepository;
	}

	@Override
	public void save(DictionaryGroup dictionaryGroup) {

	}

	@Override
	public void remove(String groupCode, String dictionaryCode) {
	}

	@Override
	public void remove(String groupCode) {
	}

	@Override
	public Optional<Dictionary> getDictionary(String groupCode, String dictionaryCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
				.map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.getDictionary(dictionaryCode));
	}

	@Override
	public Optional<DictionaryGroup> getDictionary(String groupCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		Optional<SugarcoatDictionaryGroup> dictionaries = dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode));
		return dictionaries.map(a -> a);
	}

	@Override
	public Optional<DictionaryGroup> getDictionaryGroup(String groupCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		Optional<SugarcoatDictionaryGroup> sugarcoatDictionaryGroup = dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode));
		return sugarcoatDictionaryGroup.map(a -> a);
	}

	@Override
	public Optional<String> getDictionaryName(String groupCode, String dictionaryCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
				.map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.getDictionaryName(dictionaryCode));
	}

	@Override
	public boolean existDictionary(String groupCode, String dictionaryCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
				.map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.existDictionary(dictionaryCode))
				.orElse(false);
	}

}
