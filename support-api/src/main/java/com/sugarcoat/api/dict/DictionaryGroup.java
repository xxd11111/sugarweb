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

	/**
	 */
	String getGroupCode();

	/**
	 * 获取字典组名称
	 */
	String getGroupName();

	/**
	 * 获取字典组内字典集合
	 */
	Collection<Dictionary> getDictionaries();

	/**
	 * 根据字典编码判断是否存在该字典
	 * @param dictionaryCode 字典编码
	 * @return 是否
	 */
	default boolean existDictionary(String dictionaryCode) {
		Collection<? extends Dictionary> dictionaries = getDictionaries();
		if (dictionaries == null || dictionaries.isEmpty() || dictionaryCode == null || dictionaryCode.isEmpty()) {
			return false;
		}
		return dictionaries.stream().anyMatch(dictionary -> dictionaryCode.equals(dictionary.getCode()));
	}

	/**
	 * 根据字典编码获取字典名称
	 * @param dictionaryCode 字典编码
	 * @return 字典名称
	 */
	default String getDictionaryName(String dictionaryCode) {
		if (dictionaryCode == null || dictionaryCode.isEmpty()) {
			return null;
		}
		return getDictionaries().stream().filter(dictionary -> dictionaryCode.equals(dictionary.getCode()))
				.findFirst().map(Dictionary::getName).orElse(null);
	}

	/**
	 * 根据字典编码获取字典
	 * @param dictionaryCode 字典编码
	 * @return 字典
	 */
	default Dictionary getDictionary(String dictionaryCode) {
		if (dictionaryCode == null || dictionaryCode.isEmpty()) {
			return null;
		}
		return getDictionaries().stream().filter(dictionary -> dictionaryCode.equals(dictionary.getCode()))
				.findFirst().orElse(null);
	}

}
