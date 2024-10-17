package com.sugarweb.chatAssistant;

import com.sugarweb.chatAssistant.agent.AgentManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * 聊天助手应用
 *
 * @author xxd
 * @version 1.0
 */
@SpringBootApplication
@MapperScan({"com.sugarweb.**.domain.mapper", "com.sugarweb.**.mapper"})
@EnableWebSocket
public class ChatAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatAssistantApplication.class, args);
    }

    @Bean
    public ApplicationRunner autoAgent(AgentManager agentManager) {
        return args -> agentManager.startAgent("1");
    }

}
