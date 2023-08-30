package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.DictionaryManager;
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

    private final DictionaryManager dictionaryManager;

    //    加载策略 关闭  只新增  智能合并（已修改过的不更改）  覆盖
    @Override
    public void register(List<SugarcoatDictionaryGroup> dictionaryGroups) {
        //todo 新增策略
    }


}
