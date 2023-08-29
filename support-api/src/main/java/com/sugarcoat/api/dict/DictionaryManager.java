package com.sugarcoat.api.dict;

import java.util.Collection;
import java.util.Optional;

/**
 * 字典客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/29
 */
public interface DictionaryManager {

    void save(DictionaryGroup dictionaryGroup);

    void remove(String groupCode, String dictionaryCode);

    void remove(String groupCode);

    Optional<Dictionary> getDictionary(String groupCode, String dictionaryCode);

    Optional<DictionaryGroup> getDictionary(String groupCode);

	Optional<DictionaryGroup> getDictionaryGroup(String groupCode);

	Optional<String> getDictionaryName(String groupCode, String dictionaryCode);

    boolean existDictionary(String groupCode, String dictionaryCode);

}
