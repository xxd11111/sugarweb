package com.xxd.sugarcoat.support.dev.dict.api;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author xxd
 * @version 1.0
 * @description: 基础字典信息
 * @date 2023/3/20
 */
@Data
@Entity
public class BaseDict {
    @Id
    private String id;
    private String type;
    private String code;
    private String name;
}
