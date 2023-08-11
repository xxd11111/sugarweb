package com.sugarcoat.support.dict.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.support.dict.application.DictQueryVo;
import com.sugarcoat.support.dict.application.DictionaryDTO;
import com.sugarcoat.support.dict.application.DictionaryGroupDTO;
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
 * @date 2023/4/22 10:07
 */
@Service
@RequiredArgsConstructor
public class SugarcoatDictionaryServiceImpl implements DictionaryService {

	private final SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository;

	private final SugarcoatDictionaryRepository sugarcoatDictionaryRepository;

	@Override
	public void save(DictionaryGroupDTO dictionaryGroupDTO) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = SugarcoatDictionaryFactory.create(dictionaryGroupDTO);
		sugarcoatDictionaryGroupRepository.save(sugarcoatDictionaryGroup);
	}

	@Override
	public void save(DictionaryDTO dictionaryDTO) {
		SugarcoatDictionary dictionary = new SugarcoatDictionary();
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = sugarcoatDictionaryGroupRepository
				.findById(dictionaryDTO.getGroupId()).orElseThrow(() -> new ValidateException("dict not find"));
		dictionary.setDictionaryId(dictionaryDTO.getId());
		dictionary.setDictionaryCode(dictionaryDTO.getDictionaryCode());
		dictionary.setDictionaryName(dictionaryDTO.getDictionaryName());
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
	public DictionaryDTO findByDictionaryId(String dictItemId) {
		SugarcoatDictionary sugarcoatDictionary = sugarcoatDictionaryRepository.findById(dictItemId)
				.orElseThrow(() -> new ValidateException("dictItem not find"));
		DictionaryDTO dictionaryDTO = new DictionaryDTO();
		dictionaryDTO.setId(sugarcoatDictionary.getDictionaryId());
		dictionaryDTO.setDictionaryCode(sugarcoatDictionary.getDictionaryCode());
		dictionaryDTO.setDictionaryName(sugarcoatDictionary.getDictionaryCode());
		return dictionaryDTO;
	}

	@Override
	public DictionaryGroupDTO findByGroupId(String groupId) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = sugarcoatDictionaryGroupRepository.findById(groupId)
				.orElseThrow(() -> new ValidateException("dict not find"));
		return getDictDTO(sugarcoatDictionaryGroup);
	}

	@Override
	public DictionaryGroupDTO findByGroupCode(String groupCode) {
		SugarcoatDictionaryGroup sugarcoatDictionaryGroup = sugarcoatDictionaryGroupRepository.findById(groupCode)
				.orElseThrow(() -> new ValidateException("dict not find"));
		return getDictDTO(sugarcoatDictionaryGroup);
	}

	@Override
	public PageData<DictionaryGroupDTO> findDictPage(PageDto pageDto, DictQueryVo queryVO) {
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
		Page<DictionaryGroupDTO> page = sugarcoatDictionaryGroupRepository.findAll(expression, pageRequest)
				.map(this::getDictDTO);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

	private DictionaryGroupDTO getDictDTO(SugarcoatDictionaryGroup dict) {
		Collection<SugarcoatDictionary> sugarcoatDictionaryList = dict.getDictionaries();
		List<DictionaryDTO> dictionaryDTOList = new ArrayList<>();
		for (SugarcoatDictionary sugarcoatDictionary : sugarcoatDictionaryList) {
			DictionaryDTO dictionaryDTO = new DictionaryDTO();
			dictionaryDTO.setId(sugarcoatDictionary.getDictionaryId());
			dictionaryDTO.setDictionaryCode(sugarcoatDictionary.getDictionaryCode());
			dictionaryDTO.setDictionaryName(sugarcoatDictionary.getDictionaryName());
			dictionaryDTOList.add(dictionaryDTO);
		}
		DictionaryGroupDTO dictionaryGroupDTO = new DictionaryGroupDTO();
		dictionaryGroupDTO.setId(dict.getGroupId());
		dictionaryGroupDTO.setGroupCode(dict.getGroupCode());
		dictionaryGroupDTO.setGroupName(dict.getGroupName());
		dictionaryGroupDTO.setDictionaryDTOList(dictionaryDTOList);
		return dictionaryGroupDTO;
	}

}