package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.DictionaryGroup;

import java.util.Collection;
import java.util.Optional;

/**
 * 字典缓存管理
 *
 * @author xxd
 * @since 2023/9/2 22:45
 */
public interface DictionaryCacheManager {

    /**
     * 保存缓存
     * @param groupCode 字典组编码
     * @param dictionaryCode 字典编码
     * @param dictionaryName 字典名称
     */
    void put(String groupCode, String dictionaryCode, String dictionaryName);

    /**
     * 保存缓存
     * @param dictionaryGroups 字典组列表
     */
    void put(Collection<DictionaryGroup> dictionaryGroups);

    /**
     * 获取字典
     * @param groupCode 字典组编码
     * @param dictionaryCode 字典编码
     * @return 字典名称
     */
    Optional<String> get(String groupCode, String dictionaryCode);

    /**
     * 清空字典缓存
     */
    void clean();

}
