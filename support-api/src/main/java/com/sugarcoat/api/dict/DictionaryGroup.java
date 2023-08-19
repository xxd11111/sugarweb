package com.sugarcoat.api.dict;

import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 字典组
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/29
 */
@Component
public interface DictionaryGroup {

	String getGroupCode();

	String getGroupName();

	Collection<? extends Dictionary> getDictionaries();

	default boolean existDictionary(String dictionaryCode) {
		Collection<? extends Dictionary> dictionaries = getDictionaries();
		if (dictionaries == null || dictionaries.isEmpty() || dictionaryCode == null || dictionaryCode.isEmpty()) {
			return false;
		}
		return dictionaries.stream().anyMatch(dictionary -> dictionaryCode.equals(dictionary.getCode()));
	}

	default String getDictionaryName(String dictionaryCode) {
		if (dictionaryCode == null || dictionaryCode.isEmpty()) {
			return null;
		}
		return getDictionaries().stream().filter(dictionary -> dictionaryCode.equals(dictionary.getCode()))
				.findFirst().map(Dictionary::getName).orElse(null);
	}

	default Dictionary getDictionary(String dictionaryCode) {
		if (dictionaryCode == null || dictionaryCode.isEmpty()) {
			return null;
		}
		return getDictionaries().stream().filter(dictionary -> dictionaryCode.equals(dictionary.getCode()))
				.findFirst().orElse(null);
	}

}
