package com.sugarcoat.support.dict;

import com.sugarcoat.api.BeanUtil;
import com.sugarcoat.api.dict.Dictionary;
import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.api.dict.DictionaryManager;

import java.util.Collection;
import java.util.Optional;

/**
 * 字典帮助类
 *
 * @author xxd
 * @date 2022-11-18
 */
public class DictHelper {

	private DictHelper() {
	}

	private static DictionaryManager getInstance() {
		return DictHelperInner.DICTIONARY_CLIENT;
	}

	public static Optional<Dictionary> getDictionary(String groupCode, String dictionaryCode) {
		return getInstance().getDictionary(groupCode, dictionaryCode);
	}

	public static Optional<DictionaryGroup> getDictionary(String groupCode) {
		return getInstance().getDictionary(groupCode);
	}

	public static Optional<DictionaryGroup> getDictionaryGroup(String groupCode) {
		return getInstance().getDictionaryGroup(groupCode);
	}

	public static Optional<String> getDictionaryName(String groupCode, String dictionaryCode) {
		return getInstance().getDictionaryName(groupCode, dictionaryCode);
	}

	public static boolean existDictionary(String groupCode, String dictionaryCode) {
		return getInstance().existDictionary(groupCode, dictionaryCode);
	}

	private static class DictHelperInner {

		private final static DictionaryManager DICTIONARY_CLIENT = BeanUtil.getBean(DictionaryManager.class);

	}

}
