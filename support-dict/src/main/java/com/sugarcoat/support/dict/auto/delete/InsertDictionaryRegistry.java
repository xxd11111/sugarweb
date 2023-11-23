package com.sugarcoat.support.dict.auto.delete;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.support.dict.domain.SugarcoatDictionaryManager;
import com.sugarcoat.support.orm.BooleanEnum;
import com.sugarcoat.protocol.dictionary.Dictionary;
import com.sugarcoat.protocol.dictionary.DictionaryManager;
import com.sugarcoat.protocol.exception.FrameworkException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 字典对象注册策略，只新增
 *
 * @author xxd
 * @since 2023/8/25
 */
public class InsertDictionaryRegistry implements DictionaryRegistry {

    private final DictionaryManager dictionaryManager;

    public InsertDictionaryRegistry(DictionaryManager dictionaryManager) {
        this.dictionaryManager = dictionaryManager;
    }

    /**
     * 注册策略 新增
     * 示例：
     * db        1,2,3,4
     * inner    2,5,6,7
     * 父级重复
     * db       2.1 2.2
     * inner    2.2 2.3
     * result 2.1 此2.2为db 2.3 取子集并集
     * result 5,6,7 取父级不重复
     *
     * @param dictionaryGroups 系统内置字典
     */
    @Override
    public void save(List<SugarcoatDictionaryGroup> dictionaryGroups) {
        if (CollUtil.isEmpty(dictionaryGroups)) {
            return;
        }
        Collection<DictionaryGroup> dbDictionaryGroups;
        if (dictionaryManager instanceof SugarcoatDictionaryManager sdm) {
            dbDictionaryGroups = sdm.getAll(BooleanEnum.TRUE);
        }else {
            throw new FrameworkException("cast exception,dictionaryManager is not SugarcoatDictionaryManager");
        }
        List<DictionaryGroup> result = new ArrayList<>();
        for (SugarcoatDictionaryGroup innerDictionaryGroup : dictionaryGroups) {
            for (DictionaryGroup dbDictionaryGroup : dbDictionaryGroups) {
                // 父级重复
                if (StrUtil.equals(innerDictionaryGroup.getGroupCode(), dbDictionaryGroup.getGroupCode())) {
                    //以数据库为准  获取不存在的code
                    Collection<Dictionary> dbDictionaries = dbDictionaryGroup.getDictionaries();
                    Collection<Dictionary> innerDictionaries = innerDictionaryGroup.getDictionaries();
                    for (Dictionary innerDictionary : innerDictionaries) {
                        //不存在时保存结果
                        if (!dbDictionaryGroup.existDictionary(innerDictionary.getCode())) {
                            dbDictionaries.add(innerDictionary);
                        }
                    }
                } else {
                    //取父级不重复
                    result.add(innerDictionaryGroup);
                }
            }
        }
        //保存结果
        for (DictionaryGroup dictionaryGroup : result) {
            dictionaryManager.save(dictionaryGroup);
        }
    }


}
