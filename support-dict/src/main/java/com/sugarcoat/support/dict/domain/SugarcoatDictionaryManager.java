package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.Dictionary;
import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.api.exception.FrameworkException;
import com.sugarcoat.api.exception.ServiceException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典客户端实现类
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/30
 */
public class SugarcoatDictionaryManager implements DictionaryManager {

    private final SugarcoatDictionaryGroupRepository dictionaryGroupRepository;

    public SugarcoatDictionaryManager(SugarcoatDictionaryGroupRepository dictionaryGroupRepository) {
        this.dictionaryGroupRepository = dictionaryGroupRepository;
    }

    @Override
    public void save(DictionaryGroup dictionaryGroup) {
        if (dictionaryGroup instanceof SugarcoatDictionaryGroup sdg) {
            dictionaryGroupRepository.save(sdg);
        } else {
            throw new FrameworkException("DictionaryGroup class convert error!");
        }
    }

    @Override
    public void remove(String groupCode, String dictionaryCode) {
        if (groupCode == null || groupCode.isEmpty() || dictionaryCode == null || dictionaryCode.isEmpty()) {
            return;
        }
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        SugarcoatDictionaryGroup sugarcoatDictionaryGroup = dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .orElseThrow(() -> new ServiceException("dictGroup not find!"));
        Collection<SugarcoatDictionary> dictionaries = sugarcoatDictionaryGroup.getSugarcoatDictionaries();
        List<SugarcoatDictionary> collect = dictionaries.stream()
                .filter(a -> dictionaryCode.equals(a.getCode()))
                .collect(Collectors.toList());
        sugarcoatDictionaryGroup.setSugarcoatDictionaries(collect);
        dictionaryGroupRepository.save(sugarcoatDictionaryGroup);
    }

    @Override
    public void remove(String groupCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        SugarcoatDictionaryGroup sugarcoatDictionaryGroup = dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .orElseThrow(() -> new ServiceException("dictGroup not find!"));
        dictionaryGroupRepository.delete(sugarcoatDictionaryGroup);
    }

    @Override
    public Optional<Dictionary> getDictionary(String groupCode, String dictionaryCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.getDictionary(dictionaryCode));
    }

    @Override
    public Optional<DictionaryGroup> getDictionaryGroup(String groupCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        Optional<SugarcoatDictionaryGroup> sugarcoatDictionaryGroup = dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode));
        return sugarcoatDictionaryGroup.map(a -> a);
    }

    @Override
    public Optional<String> getDictionaryName(String groupCode, String dictionaryCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.getDictionaryName(dictionaryCode));
    }

    @Override
    public boolean existDictionary(String groupCode, String dictionaryCode) {
        QSugarcoatDictionaryGroup qDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        return dictionaryGroupRepository.findOne(qDictionaryGroup.groupCode.eq(groupCode))
                .map(sugarcoatDictionaryGroup -> sugarcoatDictionaryGroup.existDictionary(dictionaryCode))
                .orElse(false);
    }

}
