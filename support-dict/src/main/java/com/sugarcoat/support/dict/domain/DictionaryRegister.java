package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.Dictionary;
import com.sugarcoat.api.dict.DictionaryGroup;

import java.util.List;

/**
 * 注册字典数据
 *
 * @author xxd
 * @since 2023/8/26
 */
public interface DictionaryRegister<T extends DictionaryGroup<K>, K extends Dictionary> {

    void register(List<T> dictionaryGroups);

}
