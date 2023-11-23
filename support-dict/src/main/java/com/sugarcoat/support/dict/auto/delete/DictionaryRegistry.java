package com.sugarcoat.support.dict.auto.delete;

import java.util.List;

/**
 * 注册字典数据
 *
 * @author xxd
 * @since 2023/8/26
 */
public interface DictionaryRegistry {

    void save(List<SugarcoatDictionaryGroup> dictionaryGroups);

}
