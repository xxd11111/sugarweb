package com.sugarweb.chatAssistant.domain;

import lombok.Data;

/**
 * 大模型信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class LlmInfo {

    private String modelId;

    private String modelName;

    private String modelType;

    private String modelPlatform;

}
