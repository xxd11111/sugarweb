package com.sugarweb.param.application;

import lombok.Data;

/**
 * 参数dto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ParamDto {

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
