package com.sugarweb.digitalHuman.service;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.stereotype.Service;

/**
 * AgentChatService
 *
 * @author xxd
 * @since 2025/4/12 21:34
 */
@Service
public class AgentChatService {

    public void chat() {
        StreamingChatLanguageModel chatModel = OllamaStreamingChatModel.builder()
                .baseUrl("http://192.168.193.151:11434")
                .modelName("qwen2.5:3b")
                .build();
    }

}
