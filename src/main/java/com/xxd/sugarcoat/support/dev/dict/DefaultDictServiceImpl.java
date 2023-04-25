package com.xxd.sugarcoat.support.dev.dict;

import com.xxd.sugarcoat.support.dev.dict.api.*;
import com.xxd.sugarcoat.support.dev.dict.model.DictGroup;
import com.xxd.sugarcoat.support.dev.dict.model.DictItem;
import com.xxd.sugarcoat.support.dev.dict.model.DictItemRepository;
import com.xxd.sugarcoat.support.dev.dict.model.DictRepository;
import com.xxd.sugarcoat.support.dev.exception.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/22 10:07
 */
public class DefaultDictServiceImpl implements DictService {
    @Autowired
    private DictRepository dictRepository;
    @Autowired
    private DictItemRepository dictItemRepository;

    @Override
    public void save(DictDTO dictDTO) {
        DictGroup dictGroup = DictFactory.create(dictDTO);
        dictRepository.save(dictGroup);
    }

    @Override
    public void removeDict(String groupId) {
        dictRepository.deleteById(groupId);
    }

    @Override
    public void removeDictItem(String dictItemId) {
        dictItemRepository.deleteById(dictItemId);
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
        DictGroup dictGroup = dictRepository.findById(groupId)
                .orElseThrow(() -> new ValidateException("dict not find"));
        List<DictItem> dictItemList = dictGroup.getDictItemList();
        List<DictItemDTO> dictItemDTOList = new ArrayList<>();
        for (DictItem dictItem : dictItemList) {
            DictItemDTO dictItemDTO = new DictItemDTO();
            dictItemDTO.setId(dictItem.getId());
            dictItemDTO.setCode(dictItem.getCode());
            dictItemDTO.setName(dictItem.getName());
            dictItemDTOList.add(dictItemDTO);
        }
        DictDTO dictDTO = new DictDTO();
        dictDTO.setId(dictGroup.getId());
        dictDTO.setGroupCode(dictGroup.getGroupCode());
        dictDTO.setGroupName(dictGroup.getGroupName());
        dictDTO.setDictItemDTOList(dictItemDTOList);
        return dictDTO;
    }

    @Override
    public DictDTO findByGroupCode(String groupCode) {
        DictGroup dictGroup = dictRepository.findById(groupCode)
                .orElseThrow(() -> new ValidateException("dict not find"));
        List<DictItem> dictItemList = dictGroup.getDictItemList();
        List<DictItemDTO> dictItemDTOList = new ArrayList<>();
        for (DictItem dictItem : dictItemList) {
            DictItemDTO dictItemDTO = new DictItemDTO();
            dictItemDTO.setId(dictItem.getId());
            dictItemDTO.setCode(dictItem.getCode());
            dictItemDTO.setName(dictItem.getName());
            dictItemDTOList.add(dictItemDTO);
        }
        DictDTO dictDTO = new DictDTO();
        dictDTO.setId(dictGroup.getId());
        dictDTO.setGroupCode(dictGroup.getGroupCode());
        dictDTO.setGroupName(dictGroup.getGroupName());
        dictDTO.setDictItemDTOList(dictItemDTOList);
        return dictDTO;
    }

}
