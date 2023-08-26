package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.Dictionary;
import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.dict.DictionaryGroup;

import java.util.Collection;
import java.util.Optional;

/**
 * 字典客户端实现类
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/30
 */
public class SugarcoatDictionaryManager implements DictionaryManager<SugarcoatDictionaryGroup, SugarcoatDictionary> {

	private final SugarcoatDictionaryGroupRepository dictionaryGroupRepository;

	public SugarcoatDictionaryManager(SugarcoatDictionaryGroupRepository dictionaryGroupRepository) {
		this.dictionaryGroupRepository = dictionaryGroupRepository;
	}

	@Override
	public void save(SugarcoatDictionaryGroup dictionaryGroup) {

	}

	@Override
	public void remove(String groupCode, String dictionaryCode) {

	}

	@Override
	public void remove(String groupCode) {

	}

	@Override
	public Optional<SugarcoatDictionary> getDictionary(String groupCode, String dictionaryCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
				.map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.getDictionary(dictionaryCode));
	}

	@Override
	public Collection<SugarcoatDictionary> getDictionary(String groupCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
				.map(SugarcoatDictionaryGroup::getDictionaries).orElse(null);
	}

	@Override
	public Optional<SugarcoatDictionaryGroup> getDictionaryGroup(String groupCode) {
		QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode));
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
