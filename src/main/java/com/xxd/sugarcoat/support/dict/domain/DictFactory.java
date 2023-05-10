package com.xxd.sugarcoat.support.dict.domain;

import cn.hutool.core.util.StrUtil;
import com.xxd.sugarcoat.support.dict.api.DictDTO;
import com.xxd.sugarcoat.support.dict.api.DictItemDTO;
import com.xxd.sugarcoat.support.servelt.exception.api.ValidateException;

import java.util.ArrayList;
import java.util.List;

/**
 * 全量操作时候采用该方式构建对象
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/25
 */
public class DictFactory {
    private static DictGroupRepository dictGroupRepository;

    public static DictGroup create(DictDTO dictDTO) {
        //code 库内唯一
        //name 库内唯一
        //code name 组内唯一
        DictGroup dictGroup;
        if (StrUtil.isNotBlank(dictDTO.getId())) {
            dictGroup = dictGroupRepository.findById(dictDTO.getId())
                    .orElseThrow(() -> new ValidateException("dict not find,id{}", dictDTO.getId()));
            dictGroup.setId(dictDTO.getId());
            dictGroup.setGroupCode(dictDTO.getGroupCode());
            dictGroup.setGroupName(dictDTO.getGroupName());
            List<DictItemDTO> dictItemDTOList = dictDTO.getDictItemDTOList();
            ArrayList<DictItem> dictItemList = new ArrayList<>();
            for (DictItemDTO dictItemDTO : dictItemDTOList) {
                DictItem dictItem = new DictItem();
                dictItem.setId(dictItemDTO.getId());
                dictItem.setCode(dictItemDTO.getCode());
                dictItem.setName(dictItemDTO.getName());
                dictItemList.add(dictItem);
            }
            dictGroup.setDictItems(dictItemList);
        } else {
            dictGroup = new DictGroup();
            dictGroup.setId(dictDTO.getId());
            dictGroup.setGroupCode(dictDTO.getGroupCode());
            dictGroup.setGroupName(dictDTO.getGroupName());
            List<DictItemDTO> dictItemDTOList = dictDTO.getDictItemDTOList();
            ArrayList<DictItem> dictItemList = new ArrayList<>();
            for (DictItemDTO dictItemDTO : dictItemDTOList) {
                DictItem dictItem = new DictItem();
                dictItem.setId(dictItemDTO.getId());
                dictItem.setCode(dictItemDTO.getCode());
                dictItem.setName(dictItemDTO.getName());
                dictItemList.add(dictItem);
            }
            dictGroup.setDictItems(dictItemList);
        }

        return dictGroup;

    }
}