package com.sugarcoat.support.dict.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.protocol.dictionary.Dictionary;
import com.sugarcoat.protocol.dictionary.DictionaryManager;

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

    private final SgcDictionaryRepository dictionaryRepository;

    public SugarcoatDictionaryManager(SgcDictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public void put(Dictionary dictionary) {
        if (dictionary instanceof SugarcoatDictionary sugarcoatDictionary) {
            dictionaryRepository.save(sugarcoatDictionary);
        }
    }

    @Override
    public void put(Collection<Dictionary> dictionaries) {
        List<SugarcoatDictionary> collect = dictionaries.stream()
                .map(a -> (SugarcoatDictionary) a)
                .collect(Collectors.toList());
        dictionaryRepository.saveAll(collect);
    }

    @Override
    public void remove(String group, String code) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.group.eq(group);
        expression.and(sugarcoatDictionary.code.eq(code));
        Iterable<SugarcoatDictionary> all = dictionaryRepository.findAll(expression);
        Collection<String> ids = new ArrayList<>();
        all.forEach(a -> ids.add(a.getId()));
        dictionaryRepository.deleteAllById(ids);
    }

    @Override
    public void removeByGroup(String group) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.group.eq(group);
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
    public Optional<Dictionary> get(String group, String code) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.group.eq(group);
        expression.and(sugarcoatDictionary.code.eq(code));
        Optional<SugarcoatDictionary> dictionary = dictionaryRepository.findOne(expression);
        return dictionary.map(a->a);
    }

    @Override
    public Collection<Dictionary> getByGroup(String group) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.group.eq(group);
        Iterable<SugarcoatDictionary> all = dictionaryRepository.findAll(expression);
        Collection<Dictionary> result = new ArrayList<>();
        for (SugarcoatDictionary dictionary : all) {
            result.add(dictionary);
        }
        return result;
    }

    @Override
    public Optional<Dictionary> getById(String id) {
        Optional<SugarcoatDictionary> dictionary = dictionaryRepository.findById(id);
        return dictionary.map(a->a);
    }

    @Override
    public Collection<Dictionary> getAll() {
        Iterable<SugarcoatDictionary> all = dictionaryRepository.findAll();
        Collection<Dictionary> result = new ArrayList<>();
        for (SugarcoatDictionary dictionary : all) {
            result.add(dictionary);
        }
        return result;
    }

    @Override
    public Optional<Dictionary> getByName(String group, String name) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.group.eq(group);
        expression.and(sugarcoatDictionary.name.eq(name));
        Optional<SugarcoatDictionary> dictionary = dictionaryRepository.findOne(expression);
        return dictionary.map(a->a);
    }

    @Override
    public boolean exist(String group, String code) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.group.eq(group);
        expression.and(sugarcoatDictionary.code.eq(code));
        return dictionaryRepository.exists(expression);
    }

    @Override
    public boolean exist(String group) {
        QSugarcoatDictionary sugarcoatDictionary = QSugarcoatDictionary.sugarcoatDictionary;
        BooleanExpression expression = sugarcoatDictionary.group.eq(group);
        return dictionaryRepository.exists(expression);
    }
}
