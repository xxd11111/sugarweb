package com.sugarweb.digitalHuman.infra.model.ollama;

import lombok.Data;

/**
 * Ollama 模型配置
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class OllamaModelConfig {

    private String baseUrl;

    private String modelName;

}
