package com.sugarcoat.support.dict.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 字典对象扫描  新增
 *
 * @author xxd
 * @since 2023/8/25
 */
@RequiredArgsConstructor
public class InsertDictionaryRegistry implements DictionaryRegistry {

    @Override
    public void register(List<SugarcoatDictionaryGroup> dictionaryGroups) {
        //todo 新增策略
    }


}
