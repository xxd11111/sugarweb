package com.sugarweb.digitalHuman;

import com.sugarweb.digitalHuman.infra.agent.PerformanceManager;
import org.mybatis.spring.annotation.MapperScan;
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
public class DigitalHumanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalHumanApplication.class, args);
    }

    @Bean
    public ApplicationRunner autoAgent(PerformanceManager performanceManager) {
        return args -> performanceManager.startStage("1");
    }

}
