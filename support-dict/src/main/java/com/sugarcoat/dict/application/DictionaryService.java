package com.sugarcoat.dict.application;

import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParameter;

import java.util.Set;

/**
 * 字典对外服务
 * @author xxd
 * @date 2022-11-21
 */
public interface DictionaryService {

    void save(DictionaryGroupDTO dictionaryGroupDTO);

    void save(DictionaryDTO dictionaryDTO);

    void removeDictionaryGroup(Set<String> groupIds);

    void removeDictionary(Set<String> dictionaryIds);

    DictionaryDTO findByDictionaryId(String dictionaryId);

    DictionaryGroupDTO findByGroupId(String groupId);

    DictionaryGroupDTO findByGroupCode(String groupCode);

    PageData<DictionaryGroupDTO> findDictPage(PageParameter pageParameter, DictQueryVO queryVO);

}
