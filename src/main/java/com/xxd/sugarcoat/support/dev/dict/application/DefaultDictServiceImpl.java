package com.xxd.sugarcoat.support.dev.dict.application;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.xxd.sugarcoat.support.dev.dict.model.DictFactory;
import com.xxd.sugarcoat.support.dev.dict.api.DictService;
import com.xxd.sugarcoat.support.dev.dict.api.*;
import com.xxd.sugarcoat.support.dev.dict.model.*;
import com.xxd.sugarcoat.support.dev.exception.ValidateException;
import com.xxd.sugarcoat.support.prod.common.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/22 10:07
 */
@Service
@RequiredArgsConstructor
public class DefaultDictServiceImpl implements DictService {
    private final DictGroupRepository dictGroupRepository;
    private final DictItemRepository dictItemRepository;

    @Override
    public void save(DictDTO dictDTO) {
        DictGroup dictGroup = DictFactory.create(dictDTO);
        dictGroupRepository.save(dictGroup);
    }

    @Override
    public void save(DictItemDTO dictItemDTO) {
        DictItem dictItem = new DictItem();
        DictGroup dictGroup = dictGroupRepository.findById(dictItemDTO.getGroupId()).orElseThrow(() -> new ValidateException("dict not find"));
        dictItem.setId(dictItem.getId());
        dictItem.setCode(dictItem.getCode());
        dictItem.setName(dictItem.getName());
        dictItem.setDictGroup(dictGroup);
        dictItemRepository.save(dictItem);
    }

    @Override
    public void removeDict(Set<String> groupIds) {
        dictGroupRepository.deleteAllById(groupIds);
    }

    @Override
    public void removeDictItem(Set<String> dictItemIds) {
        dictItemRepository.deleteAllById(dictItemIds);
    }

    @Override
    public DictItemDTO findByItemId(String dictItemId) {
        DictItem dictItem = dictItemRepository.findById(dictItemId)
                .orElseThrow(() -> new ValidateException("dictItem not find"));
        DictItemDTO dictItemDTO = new DictItemDTO();
        dictItemDTO.setId(dictItem.getId());
        dictItemDTO.setCode(dictItem.getCode());
        dictItemDTO.setName(dictItem.getName());
        return dictItemDTO;
    }

    @Override
    public DictDTO findByGroupId(String groupId) {
        DictGroup dictGroup = dictGroupRepository.findById(groupId)
                .orElseThrow(() -> new ValidateException("dict not find"));
        return getDictDTO(dictGroup);
    }

    @Override
    public DictDTO findByGroupCode(String groupCode) {
        DictGroup dictGroup = dictGroupRepository.findById(groupCode)
                .orElseThrow(() -> new ValidateException("dict not find"));
        return getDictDTO(dictGroup);
    }


    @Override
    public Page<DictDTO> pageDict(PageParam pageParam, DictQueryVO queryVO) {
        QDictGroup dictGroup = QDictGroup.dictGroup;
        //构造分页，按照创建时间降序
        PageRequest pageRequest = PageRequest
                .of(pageParam.getPageNum(), pageParam.getPageSize())
                .withSort(Sort.Direction.DESC, dictGroup.createDate.getMetadata().getName());
        //条件查询
        BooleanExpression expression = Expressions.TRUE;
        if (StrUtil.isNotEmpty(queryVO.getGroupName())) {
            expression.and(dictGroup.groupName.like(queryVO.getGroupName(), '/'));
        }
        if (StrUtil.isNotEmpty(queryVO.getGroupCode())) {
            expression.and(dictGroup.groupCode.eq(queryVO.getGroupCode()));
        }
        return dictGroupRepository.findAll(expression, pageRequest).map(this::getDictDTO);
    }

    private DictDTO getDictDTO(DictGroup dict) {
        List<DictItem> dictItemList = dict.getDictItems();
        List<DictItemDTO> dictItemDTOList = new ArrayList<>();
        for (DictItem dictItem : dictItemList) {
            DictItemDTO dictItemDTO = new DictItemDTO();
            dictItemDTO.setId(dictItem.getId());
            dictItemDTO.setCode(dictItem.getCode());
            dictItemDTO.setName(dictItem.getName());
            dictItemDTOList.add(dictItemDTO);
        }
        DictDTO dictDTO = new DictDTO();
        dictDTO.setId(dict.getId());
        dictDTO.setGroupCode(dict.getGroupCode());
        dictDTO.setGroupName(dict.getGroupName());
        dictDTO.setDictItemDTOList(dictItemDTOList);
        return dictDTO;
    }

}
