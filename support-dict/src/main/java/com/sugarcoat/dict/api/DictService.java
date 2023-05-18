package com.sugarcoat.dict.api;

import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParam;

import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public interface DictService {

    void save(DictDTO dictDTO);

    void save(DictItemDTO dictItemDTO);

    void removeDict(Set<String> groupIds);

    void removeDictItem(Set<String> dictItemIds);

    DictItemDTO findByItemId(String dictItemId);

    DictDTO findByGroupId(String groupId);

    DictDTO findByGroupCode(String groupCode);

    PageData<DictDTO> findDictPage(PageParam pageParam, DictQueryVO queryVO);

}
