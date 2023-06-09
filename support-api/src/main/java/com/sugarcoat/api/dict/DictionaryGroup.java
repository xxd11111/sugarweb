package com.sugarcoat.api.dict;

import java.util.Collection;

/**
 * 字典组
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/29
 */
public interface DictionaryGroup {

    String getGroupCode();

    String getGroupName();

    Collection<? extends Dictionary> getDictionaries();

    default boolean existDictionary(String dictionaryCode) {
        Collection<? extends Dictionary> dictionaries = getDictionaries();
        if (dictionaries == null ||
                dictionaries.isEmpty() ||
                dictionaryCode == null ||
                dictionaryCode.isEmpty()) {
            return false;
        }
        return dictionaries.stream().anyMatch(dictionary -> dictionaryCode.equals(dictionary.getDictionaryCode()));
    }

    default String getDictionaryName(String dictionaryCode) {
        if (dictionaryCode == null || dictionaryCode.isEmpty()) {
            return null;
        }
        return getDictionaries().stream()
                .filter(dictionary -> dictionaryCode.equals(dictionary.getDictionaryCode()))
                .findFirst()
                .map(Dictionary::getDictionaryName)
                .orElse(null);
    }

    default Dictionary getDictionary(String dictionaryCode) {
        if (dictionaryCode == null || dictionaryCode.isEmpty()) {
            return null;
        }
        return getDictionaries().stream()
                .filter(dictionary -> dictionaryCode.equals(dictionary.getDictionaryCode()))
                .findFirst()
                .orElse(null);
    }
}
