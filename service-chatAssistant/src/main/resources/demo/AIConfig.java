package com.sugarweb.chatAssistant.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
class AIConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(500);
        httpRequestFactory.setReadTimeout(1500);
        return new RestTemplate(httpRequestFactory);
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("模拟一个友好的聊天机器人，可以用海盗的声音回答问题")
                .build();
    }

    @Bean
    ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

}