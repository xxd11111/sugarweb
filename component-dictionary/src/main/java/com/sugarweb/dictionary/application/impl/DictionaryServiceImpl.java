package com.sugarweb.dictionary.application.impl;

import com.google.common.collect.Iterables;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.dictionary.application.DictionaryService;
import com.sugarweb.dictionary.application.dto.DictionaryDto;
import com.sugarweb.dictionary.application.dto.DictionaryQueryDto;
import com.sugarweb.dictionary.domain.Dictionary;
import com.sugarweb.dictionary.domain.QDictionary;
import com.sugarweb.dictionary.domain.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Dictionary dictionary = new Dictionary();
        dictionary.setId(dictionaryDto.getId());
        dictionary.setDictCode(dictionaryDto.getDictCode());
        dictionary.setDictName(dictionaryDto.getDictName());
        dictionary.setDictGroup(dictionaryDto.getDictGroup());
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void save(Collection<DictionaryDto> dictionaryDtos) {
        for (DictionaryDto dictionaryDto : dictionaryDtos) {
            save(dictionaryDto);
        }
    }

    private DictionaryDto toDictDTO(Dictionary dict) {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setId(dict.getId());
        dictionaryDto.setDictCode(dict.getDictCode());
        dictionaryDto.setDictName(dict.getDictName());
        dictionaryDto.setDictGroup(dict.getDictGroup());
        return dictionaryDto;
    }

    @Override
    public void removeById(String id) {
        dictionaryRepository.deleteById(id);
    }

    @Override
    public void removeByIds(Set<String> ids) {
        dictionaryRepository.deleteAllById(ids);
    }

    @Override
    public void removeByCode(String group, String code) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanExpression expression = dictionary.dictGroup.eq(group);
        expression.and(dictionary.dictCode.eq(code));
        Optional<Dictionary> dictionaryOptional = dictionaryRepository.findOne(expression);
        dictionaryOptional.ifPresent(a -> dictionaryRepository.deleteById(a.getId()));
    }

    @Override
    public void removeByGroup(String group) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanExpression expression = dictionary.dictGroup.eq(group);
        List<Dictionary> dictionaries = dictionaryRepository.findAll(expression);
        if (!Iterables.isEmpty(dictionaries)) {
            List<String> ids = dictionaries.stream().map(Dictionary::getId).toList();
            dictionaryRepository.deleteAllById(ids);
        }
    }

    @Override
    public void removeAll() {
        dictionaryRepository.deleteAll();
    }

    @Override
    public Optional<DictionaryDto> findById(String id) {
        return dictionaryRepository.findById(id).map(this::toDictDTO);
    }

    @Override
    public List<DictionaryDto> findByIds(Set<String> ids) {
        List<Dictionary> allById = dictionaryRepository.findAllById(ids);
        return allById.stream().map(this::toDictDTO).toList();
    }

    @Override
    public List<DictionaryDto> findByGroup(String group) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanExpression expression = dictionary.dictGroup.eq(group);
        return dictionaryRepository.findAll(expression).stream().map(this::toDictDTO).toList();
    }

    @Override
    public Optional<DictionaryDto> findByCode(String group, String code) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanExpression expression = dictionary.dictGroup.eq(group);
        expression.and(dictionary.dictCode.eq(code));
        return dictionaryRepository.findOne(expression).map(this::toDictDTO);
    }

    @Override
    public PageData<DictionaryDto> findPage(PageQuery pageDto, DictionaryQueryDto queryDto) {
        QDictionary dictionary = QDictionary.dictionary;
        // 构造分页，按照创建时间降序
        PageRequest pageRequest = PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
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
                .map(this::toDictDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public List<DictionaryDto> findAll() {
        return dictionaryRepository.findAll().stream().map(this::toDictDTO).toList();
    }

    @Override
    public boolean existByCode(String group, String code) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanExpression expression = dictionary.dictGroup.eq(group);
        expression.and(dictionary.dictCode.eq(code));
        return dictionaryRepository.exists(expression);
    }

    @Override
    public boolean existByGroup(String group) {
        QDictionary dictionary = QDictionary.dictionary;
        BooleanExpression expression = dictionary.dictGroup.eq(group);
        return dictionaryRepository.exists(expression);
    }

}
