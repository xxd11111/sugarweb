package com.sugarcoat.support.dict.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.orm.BooleanEnum;
import com.sugarcoat.protocol.dict.Dictionary;
import com.sugarcoat.protocol.dict.DictionaryGroup;
import com.sugarcoat.protocol.dict.DictionaryManager;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.exception.ServiceException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典客户端实现类
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/30
 */
public class SugarcoatDictionaryManager implements DictionaryManager {

    private final SgcDictionaryGroupRepository dictionaryGroupRepository;

    public SugarcoatDictionaryManager(SgcDictionaryGroupRepository dictionaryGroupRepository) {
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
    public Collection<DictionaryGroup> getAll() {
        return getAll(null);
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

    public Collection<DictionaryGroup> getAll(BooleanEnum innerFlag) {
        QSugarcoatDictionaryGroup sugarcoatDictionaryGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
        BooleanExpression expression =  Expressions.TRUE;
        if (Objects.nonNull(innerFlag)){
            expression.and(sugarcoatDictionaryGroup.innerFlag.eq(innerFlag));
        }
        Iterable<SugarcoatDictionaryGroup> dictionaryGroups = dictionaryGroupRepository.findAll(expression);
        ArrayList<DictionaryGroup> result = new ArrayList<>();
        dictionaryGroups.forEach(result::add);
        return result;
    }

}
