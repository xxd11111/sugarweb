package com.sugarcoat.support.dict.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.dictionary.Dictionary;
import com.sugarcoat.protocol.dictionary.DictionaryManager;
import com.sugarcoat.support.dict.application.dto.DictionaryQueryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryGroupDto;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.dict.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

	private final DictionaryManager dictionaryManager;

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
		dictionaryManager.remove(ids);
	}

	@Override
	public void removeGroup(String group) {
		dictionaryManager.remove(group);
	}

	@Override
	public DictionaryDto findOne(String id) {
		Dictionary dictionary = dictionaryManager.getById(id)
				.orElseThrow(() -> new ValidateException("dictItem not find"));
		DictionaryDto dictionaryDTO = new DictionaryDto();
		dictionaryDTO.setId(dictionary.getId());
		dictionaryDTO.setCode(dictionary.getCode());
		dictionaryDTO.setName(dictionary.getName());
		dictionaryDTO.setGroup(dictionary.getGroup());
		return dictionaryDTO;
	}

	@Override
	public PageData<DictionaryGroupDto> findPage(PageDto pageDto, DictionaryQueryDto queryDto) {
		QSugarcoatDictionaryGroup dictGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		// 构造分页，按照创建时间降序
		PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize())
				.withSort(Sort.Direction.DESC, dictGroup.groupCode.getMetadata().getName());
		// 条件查询
		BooleanExpression expression = Expressions.TRUE;
		if (queryDto.getGroupName() != null && !queryDto.getGroupName().isEmpty()) {
			expression.and(dictGroup.groupName.like(queryDto.getGroupName(), '/'));
		}
		if (queryDto.getGroupCode() != null && !queryDto.getGroupCode().isEmpty()) {
			expression.and(dictGroup.groupCode.eq(queryDto.getGroupCode()));
		}
		Page<DictionaryGroupDto> page = sugarcoatDictionaryGroupRepository.findAll(expression, pageRequest)
				.map(this::getDictDTO);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

	private DictionaryGroupDto getDictDTO(SugarcoatDictionaryGroup dict) {
		Collection<SugarcoatDictionary> sugarcoatDictionaryList = dict.getSugarcoatDictionaries();
		List<DictionaryDto> dictionaryDtoList = new ArrayList<>();
		for (SugarcoatDictionary sugarcoatDictionary : sugarcoatDictionaryList) {
			DictionaryDto dictionaryDTO = new DictionaryDto();
			dictionaryDTO.setId(sugarcoatDictionary.getId());
			dictionaryDTO.setCode(sugarcoatDictionary.getCode());
			dictionaryDTO.setName(sugarcoatDictionary.getName());
			dictionaryDtoList.add(dictionaryDTO);
		}
		DictionaryGroupDto dictionaryGroupDTO = new DictionaryGroupDto();
		dictionaryGroupDTO.setId(dict.getGroupId());
		dictionaryGroupDTO.setGroupCode(dict.getGroupCode());
		dictionaryGroupDTO.setGroupName(dict.getGroupName());
		dictionaryGroupDTO.setDictionaryDtoList(dictionaryDtoList);
		return dictionaryGroupDTO;
	}

}
