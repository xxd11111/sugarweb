package com.sugarweb;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author 许向东
 * @version 1.0
 */
@Component
public class DemoTask {

    private final ChatClient chatClient;

    public DemoTask(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostConstruct
    public void init() {
        String userInput = this.chatClient.prompt()
                .user("userInput")
                .call()
                .content();
        System.out.println(userInput);
    }

}
