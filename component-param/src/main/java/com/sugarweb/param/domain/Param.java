package com.sugarweb.param.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 参数实体
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class Param {
    /**
     * id
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 备注
     */
    private String comment;

}