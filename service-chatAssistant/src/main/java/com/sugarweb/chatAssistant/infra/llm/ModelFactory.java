package com.sugarweb.chatAssistant.infra.llm;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.domain.ModelInfo;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;

/**
 * ModelFactory
 *
 * @author xxd
 * @version 1.0
 */
public class ModelFactory {

    public static StreamingChatLanguageModel creatStreamingChatLanguageModel(ModelInfo modelInfo) {
        if (!ModelType.CHAT.getCode().equals(modelInfo.getModelType())) {
            throw new IllegalArgumentException(StrUtil.format("不支持的模型类型,modelId:{},modelName:{}", modelInfo.getModelId(), modelInfo.getModelName()));
        }

        if (ModelPlatform.OLLAM.getCode().equals(modelInfo.getModelPlatform())) {
            return OllamaStreamingChatModel.builder()
                    .baseUrl(modelInfo.getBaseUrl())
                    .modelName(modelInfo.getModelName())
                    .build();
        } else if (ModelPlatform.ZHI_PU.getCode().equals(modelInfo.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else if (ModelPlatform.TONG_YI.getCode().equals(modelInfo.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else {
            throw new IllegalArgumentException("暂不支持该平台");
        }
    }

    public static EmbeddingModel creatEmbeddingModel(ModelInfo modelInfo) {
        if (!ModelType.EMBEDDING.getCode().equals(modelInfo.getModelType())) {
            throw new IllegalArgumentException(StrUtil.format("不支持的模型类型,modelId:{},modelName:{}", modelInfo.getModelId(), modelInfo.getModelName()));
        }
        if (ModelPlatform.OLLAM.getCode().equals(modelInfo.getModelPlatform())) {
            return OllamaEmbeddingModel.builder()
                    .baseUrl(modelInfo.getBaseUrl())
                    .modelName(modelInfo.getModelName())
                    .build();
        } else if (ModelPlatform.ZHI_PU.getCode().equals(modelInfo.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else if (ModelPlatform.TONG_YI.getCode().equals(modelInfo.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else {
            throw new IllegalArgumentException("暂不支持该平台");
        }
    }

    public static TtsModel creatTtsModel(ModelInfo modelInfo) {
        if (!ModelType.TTS.getCode().equals(modelInfo.getModelType())) {
            throw new IllegalArgumentException(StrUtil.format("不支持的模型类型,modelId:{},modelName:{}", modelInfo.getModelId(), modelInfo.getModelName()));
        }
        return new ChatTtsModel(modelInfo.getBaseUrl());
    }

}
