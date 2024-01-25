package com.sugarweb.support.dict.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.exception.ValidateException;
import com.sugarweb.support.dict.application.DictionaryService;
import com.sugarweb.support.dict.application.dto.DictionaryDto;
import com.sugarweb.support.dict.application.dto.DictionaryQueryDto;
import com.sugarweb.support.dict.domain.Dictionary;
import com.sugarweb.support.dict.domain.QDictionary;
import com.sugarweb.support.dict.domain.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * 默认实现字典服务
 *
 * @author xxd
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    
    private final DictionaryRepository dictionaryRepository;

    @Override
    public void save(DictionaryDto dictionaryDto) {
        Dictionary sugarcoatDictionary = new Dictionary();
        sugarcoatDictionary.setId(dictionaryDto.getId());
        sugarcoatDictionary.setDictCode(dictionaryDto.getCode());
        sugarcoatDictionary.setDictName(dictionaryDto.getName());
        sugarcoatDictionary.setDictGroup(dictionaryDto.getGroup());
        this.put(sugarcoatDictionary);
    }

    @Override
    public void remove(Set<String> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void removeGroup(String group) {
        this.removeByGroup(group);
    }

    @Override
    public DictionaryDto findOne(String id) {
        Dictionary dictionary = this.getById(id)
                .orElseThrow(() -> new ValidateException("dictItem not find"));
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setId(dictionary.getId());
        dictionaryDto.setCode(dictionary.getDictCode());
        dictionaryDto.setName(dictionary.getDictName());
        dictionaryDto.setGroup(dictionary.getDictGroup());
        return dictionaryDto;
    }

    @Override
    public PageData<DictionaryDto> findPage(PageRequest pageDto, DictionaryQueryDto queryDto) {
        QDictionary dictionary = QDictionary.dictionary;
        // 构造分页，按照创建时间降序
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
                .withSort(Sort.Direction.DESC, dictionary.dictGroup.getMetadata().getName());
        // 条件查询
        BooleanExpression expression = Expressions.TRUE;
        if (queryDto.getGroupName() != null && !queryDto.getGroupName().isEmpty()) {
            expression = expression.and(dictionary.dictName.like(queryDto.getGroupName(), '/'));
        }
        if (queryDto.getGroupCode() != null && !queryDto.getGroupCode().isEmpty()) {
            expression = expression.and(dictionary.dictCode.eq(queryDto.getGroupCode()));
        }
        Page<DictionaryDto> page = dictionaryRepository.findAll(expression, pageRequest)
                .map(this::getDictDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    private DictionaryDto getDictDTO(Dictionary dict) {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setId(dict.getId());
        dictionaryDto.setCode(dict.getDictCode());
        dictionaryDto.setName(dict.getDictName());
        dictionaryDto.setGroup(dict.getDictGroup());
        return dictionaryDto;
    }

    @Override
    public void put(Dictionary dictionary) {
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void put(Collection<Dictionary> dictionaries) {
        dictionaryRepository.saveAll(dictionaries);
    }

    @Override
    public void remove(String group, String code) {
        QDictionary sugarcoatDictionary = QDictionary.dictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        expression.and(sugarcoatDictionary.dictCode.eq(code));
        Iterable<Dictionary> all = dictionaryRepository.findAll(expression);
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
        QDictionary sugarcoatDictionary = QDictionary.dictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        Iterable<Dictionary> all = dictionaryRepository.findAll(expression);
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
        QDictionary sugarcoatDictionary = QDictionary.dictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        BooleanExpression and = expression.and(sugarcoatDictionary.dictCode.eq(code));
        return dictionaryRepository.findOne(and);
    }

    @Override
    public Collection<Dictionary> getByGroup(String group) {
        QDictionary sugarcoatDictionary = QDictionary.dictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        Iterable<Dictionary> all = dictionaryRepository.findAll(expression);
        Collection<Dictionary> result = new ArrayList<>();
        for (Dictionary dictionary : all) {
            result.add(dictionary);
        }
        return result;
    }

    @Override
    public Optional<Dictionary> getById(String id) {
        return dictionaryRepository.findById(id);
    }

    @Override
    public Collection<Dictionary> getAll() {
        Iterable<Dictionary> all = dictionaryRepository.findAll();
        Collection<Dictionary> result = new ArrayList<>();
        for (Dictionary dictionary : all) {
            result.add(dictionary);
        }
        return result;
    }

    @Override
    public Optional<Dictionary> getByName(String group, String name) {
        QDictionary sugarcoatDictionary = QDictionary.dictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        expression.and(sugarcoatDictionary.dictName.eq(name));
        return dictionaryRepository.findOne(expression);
    }

    @Override
    public boolean exist(String group, String code) {
        QDictionary sugarcoatDictionary = QDictionary.dictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        expression.and(sugarcoatDictionary.dictCode.eq(code));
        return dictionaryRepository.exists(expression);
    }

    @Override
    public boolean exist(String group) {
        QDictionary sugarcoatDictionary = QDictionary.dictionary;
        BooleanExpression expression = sugarcoatDictionary.dictGroup.eq(group);
        return dictionaryRepository.exists(expression);
    }

}
