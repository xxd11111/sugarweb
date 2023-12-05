package com.sugarcoat.support.dict.auto;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.dictionary.InnerDictionary;
import com.sugarcoat.support.orm.auto.AbstractAutoRegistry;
import com.sugarcoat.protocol.dictionary.DictionaryManager;
import com.sugarcoat.support.dict.domain.SugarcoatDictionary;
import com.sugarcoat.support.dict.domain.SugarcoatDictionaryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * 字典注册处理
 *
 * @author 许向东
 * @date 2023/11/23
 */
public class DictionaryRegistryHandler extends AbstractAutoRegistry<SugarcoatDictionary> {

    private final DictionaryManager<SugarcoatDictionary> dictionaryManager;

    public DictionaryRegistryHandler(SugarcoatDictionaryManager dictionaryManager) {
        this.dictionaryManager = dictionaryManager;
    }

    @Override
    protected void insert(SugarcoatDictionary o) {
        dictionaryManager.put(o);
    }

    @Override
    protected void merge(SugarcoatDictionary db, SugarcoatDictionary scan) {
        //ignore
    }

    @Override
    protected void deleteByCondition(Collection<SugarcoatDictionary> scans) {
        Collection<SugarcoatDictionary> all = dictionaryManager.getAll();
        Collection<String> removeIds = new ArrayList<>();
        for (SugarcoatDictionary db : all) {
            boolean contain = false;
            for (SugarcoatDictionary scan : scans) {
                if (StrUtil.equals(scan.getDictGroup(), db.getDictGroup()) &&
                        StrUtil.equals(scan.getDictCode(), db.getDictCode())) {
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                removeIds.add(db.getId());
            }
        }
        dictionaryManager.removeByIds(removeIds);
    }

    @Override
    protected SugarcoatDictionary selectOne(SugarcoatDictionary o) {
        return dictionaryManager.get(o.getDictGroup(), o.getDictCode()).orElse(null);
    }

    @Override
    public Collection<SugarcoatDictionary> scan() {
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("", InnerDictionary.class);
        for (Class<?> clazz : classes) {
            InnerDictionary annotation = clazz.getAnnotation(InnerDictionary.class);
            Object[] enumConstants = clazz.getEnumConstants();

        }
        return null;
    }
}
