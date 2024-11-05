package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 大模型信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ModelInfo {
    @TableId
    private String modelId;

    private String modelPlatform;

    private String modelName;

    private String modelType;

    private String baseUrl;

    private String apiKey;

    private Integer dimension;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
