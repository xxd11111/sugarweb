package com.sugarcoat.support.dict.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.support.dict.application.DictQueryVo;
import com.sugarcoat.support.dict.application.DictionaryDto;
import com.sugarcoat.support.dict.application.DictionaryGroupDto;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.api.exception.ValidateException;
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

	private final SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository;

	private final SugarcoatDictionaryRepository sugarcoatDictionaryRepository;

	@Override
	public void save(DictionaryGroupDto dictionaryGroupDto) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = SugarcoatDictionaryFactory.create(dictionaryGroupDto);
		sugarcoatDictionaryGroupRepository.save(sugarcoatDictionaryGroup);
	}

	@Override
	public void save(DictionaryDto dictionaryDto) {
		SugarcoatDictionary dictionary = new SugarcoatDictionary();
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = sugarcoatDictionaryGroupRepository
				.findById(dictionaryDto.getGroupId()).orElseThrow(() -> new ValidateException("dict not find"));
		dictionary.setDictionaryId(dictionaryDto.getId());
		dictionary.setCode(dictionaryDto.getCode());
		dictionary.setName(dictionaryDto.getName());
		dictionary.setDictionaryGroup(sugarcoatDictionaryGroup);
		sugarcoatDictionaryRepository.save(dictionary);
	}

	@Override
	public void removeDictionaryGroup(Set<String> groupIds) {
		sugarcoatDictionaryGroupRepository.deleteAllById(groupIds);
	}

	@Override
	public void removeDictionary(Set<String> dictItemIds) {
		sugarcoatDictionaryRepository.deleteAllById(dictItemIds);
	}

	@Override
	public DictionaryDto findByDictionaryId(String dictItemId) {
		SugarcoatDictionary sugarcoatDictionary = sugarcoatDictionaryRepository.findById(dictItemId)
				.orElseThrow(() -> new ValidateException("dictItem not find"));
		DictionaryDto dictionaryDTO = new DictionaryDto();
		dictionaryDTO.setId(sugarcoatDictionary.getDictionaryId());
		dictionaryDTO.setCode(sugarcoatDictionary.getCode());
		dictionaryDTO.setName(sugarcoatDictionary.getCode());
		return dictionaryDTO;
	}

	@Override
	public DictionaryGroupDto findByGroupId(String groupId) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = sugarcoatDictionaryGroupRepository.findById(groupId)
				.orElseThrow(() -> new ValidateException("dict not find"));
		return getDictDTO(sugarcoatDictionaryGroup);
	}

	@Override
	public DictionaryGroupDto findByGroupCode(String groupCode) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = sugarcoatDictionaryGroupRepository.findById(groupCode)
				.orElseThrow(() -> new ValidateException("dict not find"));
		return getDictDTO(sugarcoatDictionaryGroup);
	}

	@Override
	public PageData<DictionaryGroupDto> findDictPage(PageDto pageDto, DictQueryVo queryVO) {
		QSugarcoatDictionaryGroup dictGroup = QSugarcoatDictionaryGroup.sugarcoatDictionaryGroup;
		// 构造分页，按照创建时间降序
		PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize())
				.withSort(Sort.Direction.DESC, dictGroup.groupCode.getMetadata().getName());
		// 条件查询
		BooleanExpression expression = Expressions.TRUE;
		if (queryVO.getGroupName() != null && !queryVO.getGroupName().isEmpty()) {
			expression.and(dictGroup.groupName.like(queryVO.getGroupName(), '/'));
		}
		if (queryVO.getGroupCode() != null && !queryVO.getGroupCode().isEmpty()) {
			expression.and(dictGroup.groupCode.eq(queryVO.getGroupCode()));
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
			dictionaryDTO.setId(sugarcoatDictionary.getDictionaryId());
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
