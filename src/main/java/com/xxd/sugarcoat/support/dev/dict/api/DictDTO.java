package com.xxd.sugarcoat.support.dev.dict.api;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/19 23:21
 */
@Data
public class DictDTO {
    @Column(length = 32)
    private String groupCode;
    @Column(length = 32)
    private String groupName;
    @Column(length = 32)
    private String code;
    @Column(length = 32)
    private String name;
}
