package com.xxd.sugarcoat.support.dev.dict.api;

import com.xxd.sugarcoat.support.prod.common.PageParam;

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

    Object pageDict(PageParam pageParam, DictQueryVO queryVO);

}
