package com.sugarcoat.support.dict.domain;

import cn.hutool.core.collection.CollUtil;
import com.sugarcoat.protocol.dict.DictionaryGroup;
import com.sugarcoat.protocol.dict.DictionaryManager;

import java.util.List;

/**
 * 字典对象扫描  合并（组内合并，组外不管）
 *
 * @author xxd
 * @since 2023/8/25
 */
public class MergeDictionaryRegistry implements DictionaryRegistry {

    private final DictionaryManager dictionaryManager;

    public MergeDictionaryRegistry(DictionaryManager dictionaryManager) {
        this.dictionaryManager = dictionaryManager;
    }

    /**
     * 注册策略 合并
     * 示例：
     *     //db        1,2,3,4
     *     //inner    2,5,6,7
     *     //result 2,5,6,7 取inner
     *
     * @param dictionaryGroups 系统内置字典
     */
    @Override
    public void register(List<SugarcoatDictionaryGroup> dictionaryGroups) {
        if (CollUtil.isEmpty(dictionaryGroups)) {
            return;
        }
        //保存结果
        for (DictionaryGroup dictionaryGroup : dictionaryGroups) {
            dictionaryManager.save(dictionaryGroup);
        }
    }


}
