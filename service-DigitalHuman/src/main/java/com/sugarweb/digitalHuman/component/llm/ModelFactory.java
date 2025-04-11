package com.sugarweb.digitalHuman.component.llm;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.entity.Model;
import com.sugarweb.digitalHuman.component.tts.ChatTtsModel;
import com.sugarweb.digitalHuman.component.tts.TtsModel;
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

    public static StreamingChatLanguageModel creatStreamingChatLanguageModel(String modelId) {
        Model model = Db.getById(modelId, Model.class);
        return creatStreamingChatLanguageModel(model);
    }

    /**
     * 创建聊天模型
     */
    public static StreamingChatLanguageModel creatStreamingChatLanguageModel(Model model) {
        if (!ModelType.CHAT.getValue().equals(model.getModelType())) {
            throw new IllegalArgumentException(StrUtil.format("不支持的模型类型,modelId:{},modelName:{}", model.getModelId(), model.getModelName()));
        }

        if (ModelPlatform.OLLAMA.getValue().equals(model.getModelPlatform())) {
            return OllamaStreamingChatModel.builder()
                    .baseUrl(model.getBaseUrl())
                    .modelName(model.getModelName())
                    .build();
        } else if (ModelPlatform.ZHI_PU.getValue().equals(model.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else if (ModelPlatform.TONG_YI.getValue().equals(model.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else {
            throw new IllegalArgumentException("暂不支持该平台");
        }
    }

    public static EmbeddingModel creatEmbeddingModel(String modelId) {
        Model model = Db.getById(modelId, Model.class);
        return creatEmbeddingModel(model);
    }

    /**
     * 创建嵌入模型
     */
    public static EmbeddingModel creatEmbeddingModel(Model model) {
        if (!ModelType.EMBEDDING.getValue().equals(model.getModelType())) {
            throw new IllegalArgumentException(StrUtil.format("不支持的模型类型,modelId:{},modelName:{}", model.getModelId(), model.getModelName()));
        }
        if (ModelPlatform.OLLAMA.getValue().equals(model.getModelPlatform())) {
            return OllamaEmbeddingModel.builder()
                    .baseUrl(model.getBaseUrl())
                    .modelName(model.getModelName())
                    .build();
        } else if (ModelPlatform.ZHI_PU.getValue().equals(model.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else if (ModelPlatform.TONG_YI.getValue().equals(model.getModelPlatform())) {
            throw new IllegalArgumentException("暂不支持该平台");
        } else {
            throw new IllegalArgumentException("暂不支持该平台");
        }
    }

    public static TtsModel creatTtsModel(String modelId) {
        Model model = Db.getById(modelId, Model.class);
        return creatTtsModel(model);
    }

    /**
     * 创建语音转换模型
     */
    public static TtsModel creatTtsModel(Model model) {
        if (!ModelType.TTS.getValue().equals(model.getModelType())) {
            throw new IllegalArgumentException(StrUtil.format("不支持的模型类型,modelId:{},modelName:{}", model.getModelId(), model.getModelName()));
        }
        return new ChatTtsModel(model.getBaseUrl());
    }

}
