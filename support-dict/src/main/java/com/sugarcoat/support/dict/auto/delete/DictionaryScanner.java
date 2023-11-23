package com.sugarcoat.support.dict.auto.delete;

import com.sugarcoat.support.dict.domain.SugarcoatDictionary;

import java.util.List;

/**
 * 扫描项目中字典数据
 *
 * @author xxd
 * @since 2023/8/26
 */
public interface DictionaryScanner {

    /**
     * 扫描系统中存在的内置字典
     *
     * @return 字典列表
     */
    List<SugarcoatDictionary> scan();

}
