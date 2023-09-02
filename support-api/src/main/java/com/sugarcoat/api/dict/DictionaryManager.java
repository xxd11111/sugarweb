package com.sugarcoat.api.dict;

import java.util.Optional;

/**
 * 字典客户端
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/29
 */
public interface DictionaryManager {

    /**
     * 保存字典组
     *
     * @param dictionaryGroup 字典组
     */
    void save(DictionaryGroup dictionaryGroup);

    /**
     * 删除字典组
     *
     * @param groupCode      字典组编码
     * @param dictionaryCode 字典编码
     */
    void remove(String groupCode, String dictionaryCode);

    /**
     * 删除字典
     *
     * @param groupCode 字典组编码
     */
    void remove(String groupCode);

    /**
     * 获取字典
     *
     * @param groupCode      字典组编码
     * @param dictionaryCode 字典编码
     * @return 字典
     */
    Optional<Dictionary> getDictionary(String groupCode, String dictionaryCode);

    /**
     * 根据字典组编码获取字典组
     *
     * @param groupCode 字典组编码
     * @return 字典组
     */
    Optional<DictionaryGroup> getDictionaryGroup(String groupCode);

    /**
     * 获取字典名称
     *
     * @param groupCode      字典组编码
     * @param dictionaryCode 字典编码
     * @return 字典名称
     */
    Optional<String> getDictionaryName(String groupCode, String dictionaryCode);

    /**
     * 是否存在该字典
     *
     * @param groupCode      字典组编码
     * @param dictionaryCode 字典编码
     * @return 是否
     */
    boolean existDictionary(String groupCode, String dictionaryCode);

}
