package com.sugarcoat.api.dict;

import java.util.Collection;

/**
 * 字典客户端 只做查询
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/29
 */
public interface DictionaryManager {

    Dictionary getDictionary(String groupCode, String dictionaryCode);

    Collection<? extends Dictionary> getDictionary(String groupCode);

    DictionaryGroup getDictionaryGroup(String groupCode);

    String getDictionaryName(String groupCode, String dictionaryCode);

    boolean existDictionary(String groupCode, String dictionaryCode);

}
