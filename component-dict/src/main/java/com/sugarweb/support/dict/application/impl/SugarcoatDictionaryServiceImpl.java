package com.sugarweb.support.dict.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.support.dict.api.DictionaryManager;
import com.sugarweb.exception.ValidateException;
import com.sugarweb.support.dict.application.DictionaryService;
import com.sugarweb.support.dict.application.dto.DictionaryDto;
import com.sugarweb.support.dict.application.dto.DictionaryQueryDto;
import com.sugarweb.support.dict.domain.QSugarcoatDictionary;
import com.sugarweb.support.dict.domain.BaseDictionaryRepository;
import com.sugarweb.support.dict.domain.SugarcoatDictionary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 默认实现字典服务
 *
 * @author xxd
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SugarcoatDictionaryServiceImpl implements DictionaryService {

    private final DictionaryManager<SugarcoatDictionary> dictionaryManager;
    private final BaseDictionaryRepository sgcDictionaryRepository;

    @Override
    public void save(DictionaryDto dictionaryDto) {
        SugarcoatDictionary sugarcoatDictionary = new SugarcoatDictionary();
        sugarcoatDictionary.setId(dictionaryDto.getId());
        sugarcoatDictionary.setDictCode(dictionaryDto.getCode());
        sugarcoatDictionary.setDictName(dictionaryDto.getName());
        sugarcoatDictionary.setDictGroup(dictionaryDto.getGroup());
        dictionaryManager.put(sugarcoatDictionary);
    }

    @Override
    public void remove(Set<String> ids) {
        dictionaryManager.removeByIds(ids);
    }

    @Override
    public void removeGroup(String group) {
        dictionaryManager.removeByGroup(group);
    }

    @Override
    public DictionaryDto findOne(String id) {
        SugarcoatDictionary dictionary = dictionaryManager.getById(id)
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
        QSugarcoatDictionary dictionary = QSugarcoatDictionary.sugarcoatDictionary;
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
        Page<DictionaryDto> page = sgcDictionaryRepository.findAll(expression, pageRequest)
                .map(this::getDictDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    private DictionaryDto getDictDTO(SugarcoatDictionary dict) {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setId(dict.getId());
        dictionaryDto.setCode(dict.getDictCode());
        dictionaryDto.setName(dict.getDictName());
        dictionaryDto.setGroup(dict.getDictGroup());
        return dictionaryDto;
    }

}
