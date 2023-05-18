package com.sugarcoat.dict.api;

import lombok.Data;

import java.util.List;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/19 23:21
 */
@Data
public class DictDTO {
    private String id;
    private String groupCode;
    private String groupName;
    private List<DictItemDTO> dictItemDTOList;

}
