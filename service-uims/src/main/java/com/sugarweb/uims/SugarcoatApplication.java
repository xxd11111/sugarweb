package com.sugarweb.uims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 *
 * @author xxd
 * @version 1.0
 */
@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties
public class SugarcoatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SugarcoatApplication.class, args);
    }

}
