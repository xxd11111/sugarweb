package com.sugarcoat.dict.api;

import lombok.Data;

@Data
public class DictItemDTO {
    private String id;
    private String code;
    private String name;
    private String groupId;
}