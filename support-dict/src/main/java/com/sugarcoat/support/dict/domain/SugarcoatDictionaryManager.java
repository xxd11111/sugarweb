package com.sugarcoat.support.dict.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.protocol.dictionary.DictionaryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * 字典客户端实现类
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/30
 */
public class SugarcoatDictionaryManager implements DictionaryManager<SugarcoatDictionary> {

    private final SgcDictionaryRepository dictionaryRepository;

    public SugarcoatDictionaryManager(SgcDictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public void put(SugarcoatDictionary dictionary) {
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void put(Collection<SugarcoatDictionary> dictionaries) {
        dictionaryRepository.saveAll(dictionaries);
    }

    @Override
    public void remove(String group, String code) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        expression.and(sugarcoatDictionary.dictCode.eq(code));
        Iterable<SugarcoatDictionary> all = dictionaryRepository.findAll(expression);
        Collection<String> ids = new ArrayList<>();
        all.forEach(a -> ids.add(a.getId()));
        dictionaryRepository.deleteAllById(ids);
    }

    @Override
    public void removeAll() {
        dictionaryRepository.deleteAll();
    }

    @Override
    public void removeByGroup(String group) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        Iterable<SugarcoatDictionary> all = dictionaryRepository.findAll(expression);
        Collection<String> ids = new ArrayList<>();
        all.forEach(a -> ids.add(a.getId()));
        dictionaryRepository.deleteAllById(ids);
    }

    @Override
    public void removeById(String id) {
        dictionaryRepository.deleteById(id);
    }

    @Override
    public void removeByIds(Collection<String> ids) {
        dictionaryRepository.deleteAllById(ids);
    }

    @Override
    public Optional<SugarcoatDictionary> get(String group, String code) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        BooleanExpression and = expression.and(sugarcoatDictionary.dictCode.eq(code));
        return dictionaryRepository.findOne(and);
    }

    @Override
    public Collection<SugarcoatDictionary> getByGroup(String group) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        Iterable<SugarcoatDictionary> all = dictionaryRepository.findAll(expression);
        Collection<SugarcoatDictionary> result = new ArrayList<>();
        for (SugarcoatDictionary dictionary : all) {
            result.add(dictionary);
        }
        return result;
    }

    @Override
    public Optional<SugarcoatDictionary> getById(String id) {
        return dictionaryRepository.findById(id);
    }

    @Override
    public Collection<SugarcoatDictionary> getAll() {
        Iterable<SugarcoatDictionary> all = dictionaryRepository.findAll();
        Collection<SugarcoatDictionary> result = new ArrayList<>();
        for (SugarcoatDictionary dictionary : all) {
            result.add(dictionary);
        }
        return result;
    }

    @Override
    public Optional<SugarcoatDictionary> getByName(String group, String name) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        expression.and(sugarcoatDictionary.dictName.eq(name));
        return dictionaryRepository.findOne(expression);
    }

    @Override
    public boolean exist(String group, String code) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        expression.and(sugarcoatDictionary.dictCode.eq(code));
        return dictionaryRepository.exists(expression);
    }

    @Override
    public boolean exist(String group) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        return dictionaryRepository.exists(expression);
    }
}
