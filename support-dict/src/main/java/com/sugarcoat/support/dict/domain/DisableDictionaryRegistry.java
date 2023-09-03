package com.sugarcoat.support.dict.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 字典对象扫描  关闭
 *
 * @author xxd
 * @since 2023/8/25
 */
@RequiredArgsConstructor
public class DisableDictionaryRegistry implements DictionaryRegistry {

    public void register(List<SugarcoatDictionaryGroup> dictGroups) {
        //关闭
    }

}
