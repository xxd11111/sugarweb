package com.sugarcoat.protocol.dictionary;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * 字典管理者
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/29
 */
public interface DictionaryManager {

    void put(Dictionary dictionary);

    void put(Collection<Dictionary> dictionaries);

    /**
     * 删除字典项
     *
     * @param group 字典组编码
     * @param code  字典编码
     */
    void remove(String group, String code);

    /**
     * 删除字典组
     *
     * @param group 字典组编码
     */
    void removeByGroup(String group);

    void removeById(String id);

    void removeByIds(Collection<String> ids);

    /**
     * 获取字典
     *
     * @param group 字典组编码
     * @param code  字典编码
     * @return 字典
     */
    Optional<Dictionary> get(String group, String code);

    Collection<Dictionary> getByGroup(String group);

    Optional<Dictionary> getById(String group);

    /**
     * 获取所有字典组字典
     *
     * @return 字典组集合
     */
    Collection<Dictionary> getAll();

    /**
     * 获取字典名称
     *
     * @param code 字典组编码
     * @param name 字典编码
     * @return 字典名称
     */
    Optional<Dictionary> getByName(String code, String name);

    /**
     * 是否存在该字典
     *
     * @param group 字典组编码
     * @param code  字典编码
     * @return 是否
     */
    boolean exist(String group, String code);

    boolean exist(String group);

}
