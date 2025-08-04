package com.sugarweb.digitalHuman.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模型信息
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Model extends BaseEntity {
    @TableId
    private String modelId;

    private String modelType;

    private String modelName;

    private String modelPlatform;

    private String baseUrl;

    private String apiKey;

    private Integer dimension;

    private String modelConfig;

}
