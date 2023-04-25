package com.xxd.sugarcoat.support.dev.dict.api;

import java.util.List;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public interface DictService {

    void save(DictDTO dictDTO);

    void removeDict(String dictId);

    void removeDictItem(String dictItemId);

    DictItemDTO findByItemId(String dictItemId);

    DictDTO findByGroupId(String groupId);

    DictDTO findByGroupCode(String groupCode);
}
