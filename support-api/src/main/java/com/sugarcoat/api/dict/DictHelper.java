package com.sugarcoat.api.dict;

import com.sugarcoat.api.BeanUtil;

import java.util.Collection;

/**
 * @author xxd
 * @description 字典工具类
 * @date 2022-11-18
 */
public class DictHelper {

	private DictHelper() {
	}

	private static DictionaryManager getInstance() {
		return DictHelperInner.DICTIONARY_CLIENT;
	}

	public static Dictionary getDictionary(String groupCode, String dictionaryCode) {
		return getInstance().getDictionary(groupCode, dictionaryCode);
	}

	public static Collection<? extends Dictionary> getDictionary(String groupCode) {
		return getInstance().getDictionary(groupCode);
	}

	public static DictionaryGroup getDictionaryGroup(String groupCode) {
		return getInstance().getDictionaryGroup(groupCode);
	}

	public static String getDictionaryName(String groupCode, String dictionaryCode) {
		return getInstance().getDictionaryName(groupCode, dictionaryCode);
	}

	public static boolean existDictionary(String groupCode, String dictionaryCode) {
		return getInstance().existDictionary(groupCode, dictionaryCode);
	}

	private static class DictHelperInner {

		private final static DictionaryManager DICTIONARY_CLIENT = BeanUtil.getBean(DictionaryManager.class);

	}

}
