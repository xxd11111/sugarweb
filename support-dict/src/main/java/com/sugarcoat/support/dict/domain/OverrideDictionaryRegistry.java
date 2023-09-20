package com.sugarcoat.support.dict.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.common.BooleanFlag;
import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.server.exception.FrameworkException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 字典对象扫描  覆盖（全部以inner为准，清空，保存）
 *
 * @author xxd
 * @since 2023/8/25
 */
public class OverrideDictionaryRegistry implements DictionaryRegistry {

    //todo 换成 SugarcoatDictionaryRepository
    private final DictionaryManager dictionaryManager;

    public OverrideDictionaryRegistry(DictionaryManager dictionaryManager) {
        this.dictionaryManager = dictionaryManager;
    }

    /**
     * 注册策略
     * 示例：
     *     //db        1,2,3,4
     *     //inner    2,5,6,7
     *     //result 2,5,6,7保存，1，3，4删除
     *
     * @param dictionaryGroups 系统内置字典
     */
    @Override
    public void register(List<SugarcoatDictionaryGroup> dictionaryGroups) {
        if (CollUtil.isEmpty(dictionaryGroups)) {
            return;
        }
        Collection<DictionaryGroup> dbDictionaryGroups;
        if (dictionaryManager instanceof SugarcoatDictionaryManager sdm) {
            dbDictionaryGroups = sdm.getAll(BooleanFlag.TRUE);
        }else {
            throw new FrameworkException("cast exception,dictionaryManager is not SugarcoatDictionaryManager");
        }
        List<String> deleteGroupCodes = new ArrayList<>();

        for (DictionaryGroup dbDictionaryGroup : dbDictionaryGroups) {
            for (SugarcoatDictionaryGroup innerDictionaryGroup : dictionaryGroups) {
                if (!StrUtil.equals(dbDictionaryGroup.getGroupCode(), innerDictionaryGroup.getGroupCode())){
                    deleteGroupCodes.add(dbDictionaryGroup.getGroupCode());
                }
            }
        }
        //删除未匹配项
        for (String groupCode : deleteGroupCodes) {
            dictionaryManager.remove(groupCode);
        }
        //保存结果
        for (DictionaryGroup dictionaryGroup : dictionaryGroups) {
            dictionaryManager.save(dictionaryGroup);
        }
    }


}
