package com.sugarweb.chatAssistant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 聊天助手应用
 *
 * @author 许向东
 * @version 1.0
 */
@SpringBootApplication
@MapperScan({"com.sugarweb.**.domain.mapper"})
public class ChatAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatAssistantApplication.class, args);
    }

}
