package com.xxd.sugarcoat.support.dev.dict.api;

import com.xxd.sugarcoat.support.dev.exception.ValidateException;

import java.util.List;
import java.util.Map;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/22 10:07
 */
public class DefaultDictServiceImpl implements DictService {
    DictRepository dictRepository;

    @Override
    public void save(DictDTO dictDTO) {
        DictGroup dictGroup = new DictGroup();
        dictGroup.setGroupCode(dictDTO.getGroupCode());
        dictGroup.setGroupName(dictDTO.getGroupName());
        List<DictDTO.DictItemDTO> dictItemDTOList = dictDTO.getDictItemDTOList();

        dictRepository.save(dictGroup);
    }

    @Override
    public void update(DictDTO dictDTO) {
        DictGroup dictGroup = dictRepository.findById(dictDTO.getId())
                .orElseThrow(() -> new ValidateException("dict not find,id{}", dictDTO.getId()));
        dictGroup.setGroupCode(dictDTO.getGroupCode());
        dictGroup.setGroupName(dictDTO.getGroupName());
        dictGroup.addDictItem();
        List<DictItem> dictItemList = dictGroup.getDictItemList();
        dictItemList.

        List<DictDTO.DictItemDTO> dictItemDTOList = dictDTO.getDictItemDTOList();

    }

    @Override
    public void remove(String groupId) {
        dictRepository.deleteById(groupId);
    }

    @Override
    public void remove(String type, String code) {

    }

}
