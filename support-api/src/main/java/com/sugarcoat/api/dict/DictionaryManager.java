package com.sugarcoat.api.dict;

import java.util.Collection;
import java.util.Optional;

/**
 * 字典客户端
 * todo DictionaryManager<T extends DictionaryGroup<K>, K extends Dictionary> 过于冗长  使用过于恶心
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/29
 */
public interface DictionaryManager<T extends DictionaryGroup<K>, K extends Dictionary> {

    void save(T dictionaryGroup);

    void remove(String groupCode, String dictionaryCode);

    void remove(String groupCode);

    Optional<K> getDictionary(String groupCode, String dictionaryCode);

    Collection<K> getDictionary(String groupCode);

	Optional<T> getDictionaryGroup(String groupCode);

	Optional<String> getDictionaryName(String groupCode, String dictionaryCode);

    boolean existDictionary(String groupCode, String dictionaryCode);

}
