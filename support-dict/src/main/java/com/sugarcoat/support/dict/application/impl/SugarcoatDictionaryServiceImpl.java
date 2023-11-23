package com.sugarcoat.support.dict.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.dictionary.DictionaryManager;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryQueryDto;
import com.sugarcoat.support.dict.domain.QSugarcoatDictionary;
import com.sugarcoat.support.dict.domain.SgcDictionaryRepository;
import com.sugarcoat.support.dict.domain.SugarcoatDictionary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 默认实现字典服务
 *
 * @author xxd
 * @since 2023/4/22 10:07
 */
@Service
@RequiredArgsConstructor
public class SugarcoatDictionaryServiceImpl implements DictionaryService {

    private final DictionaryManager<SugarcoatDictionary> dictionaryManager;
    private final SgcDictionaryRepository sgcDictionaryRepository;

    @Override
    public void save(DictionaryDto dictionaryDto) {
        SugarcoatDictionary sugarcoatDictionary = new SugarcoatDictionary();
        sugarcoatDictionary.setId(dictionaryDto.getId());
        sugarcoatDictionary.setCode(dictionaryDto.getCode());
        sugarcoatDictionary.setName(dictionaryDto.getName());
        sugarcoatDictionary.setGroup(dictionaryDto.getGroup());
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
        dictionaryDto.setCode(dictionary.getCode());
        dictionaryDto.setName(dictionary.getName());
        dictionaryDto.setGroup(dictionary.getGroup());
        return dictionaryDto;
    }

    @Override
    public PageData<DictionaryDto> findPage(PageDto pageDto, DictionaryQueryDto queryDto) {
        QSugarcoatDictionary dictionary = QSugarcoatDictionary.sugarcoatDictionary;
        // 构造分页，按照创建时间降序
        PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize())
                .withSort(Sort.Direction.DESC, dictionary.group.getMetadata().getName());
        // 条件查询
        BooleanExpression expression = Expressions.TRUE;
        if (queryDto.getGroupName() != null && !queryDto.getGroupName().isEmpty()) {
            expression.and(dictionary.name.like(queryDto.getGroupName(), '/'));
        }
        if (queryDto.getGroupCode() != null && !queryDto.getGroupCode().isEmpty()) {
            expression.and(dictionary.code.eq(queryDto.getGroupCode()));
        }
        Page<DictionaryDto> page = sgcDictionaryRepository.findAll(expression, pageRequest)
                .map(this::getDictDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    private DictionaryDto getDictDTO(SugarcoatDictionary dict) {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setId(dict.getId());
        dictionaryDto.setCode(dict.getCode());
        dictionaryDto.setName(dict.getName());
        dictionaryDto.setGroup(dict.getGroup());
        return dictionaryDto;
    }

}
