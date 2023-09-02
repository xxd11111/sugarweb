package com.sugarcoat.support.dict.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 字典对象扫描  智能合并
 *
 * @author xxd
 * @since 2023/8/25
 */
@RequiredArgsConstructor
public class MergeDictionaryRegistry implements DictionaryRegistry {

    @Override
    public void register(List<SugarcoatDictionaryGroup> dictionaryGroups) {
        //todo 智能合并策略
    }


}
