package com.sugarcoat.dict.domain;

import com.sugarcoat.dict.api.Dictionary;
import com.sugarcoat.dict.api.DictionaryManager;
import com.sugarcoat.dict.api.DictionaryGroup;

import java.util.Collection;

/**
 * 字典客户端实现类
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/30
 */
public class SugarcoatDictionaryManager implements DictionaryManager {

    private final SugarcoatDictionaryGroupRepository dictionaryGroupRepository;

    public SugarcoatDictionaryManager(SugarcoatDictionaryGroupRepository dictionaryGroupRepository) {
        this.dictionaryGroupRepository = dictionaryGroupRepository;
    }

    @Override
    public Dictionary getDictionary(String groupCode, String dictionaryCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.getDictionary(dictionaryCode))
                .orElse(null);
    }

    @Override
    public Collection<? extends Dictionary> getDictionary(String groupCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .map(SugarcoatDictionaryGroup::getDictionaries)
                .orElse(null);
    }

    @Override
    public DictionaryGroup getDictionaryGroup(String groupCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .orElse(null);
    }

    @Override
    public String getDictionaryName(String groupCode, String dictionaryCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.getDictionaryName(dictionaryCode))
                .orElse(null);
    }

    @Override
    public boolean existDictionary(String groupCode, String dictionaryCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.existDictionary(dictionaryCode))
                .orElse(false);
    }
}
