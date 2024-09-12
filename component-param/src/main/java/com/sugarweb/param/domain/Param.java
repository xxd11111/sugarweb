package com.sugarweb.param.domain;

import lombok.Data;

/**
 * 参数实体
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class Param {
    /**
     * id
     */
    private String paramId;

    /**
     * 编码
     */
    private String paramCode;

    /**
     * 名称
     */
    private String paramName;

    /**
     * 值
     */
    private String paramValue;

    /**
     * 备注
     */
    private String paramComment;

}