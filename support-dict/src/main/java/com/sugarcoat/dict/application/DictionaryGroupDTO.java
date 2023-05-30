package com.sugarcoat.dict.application;

import lombok.Data;

import java.util.List;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/19 23:21
 */
@Data
public class DictionaryGroupDTO {
    private String id;
    private String groupCode;
    private String groupName;
    private List<DictionaryDTO> dictionaryDTOList;

}
