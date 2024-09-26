package com.sugarweb.chatAssistant.application;

import cn.hutool.core.util.StrUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author xxd
 * @since 2024/9/26 21:34
 */
@Slf4j
public class RagService {

    public static void main(String[] args) {
        // EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
        //         .baseUrl("http://192.168.193.151:11434")
        //         .modelName("nomic-embed-text")
        //         .build();
        OllamaChatModel model = OllamaChatModel.builder()
                .logRequests(true)
                .logResponses(true)
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:3b")
                .build();

        // ChatMemory chatMemory = MessageWindowChatMemory.builder()
        //         .id("12345")
        //         .maxMessages(10)
        //         .chatMemoryStore(new PersistentChatMemoryStore())
        //         .build();


        System.out.println(model.generate("测试测试，你的IQ是多少？"));
        System.out.println();
    }

}
