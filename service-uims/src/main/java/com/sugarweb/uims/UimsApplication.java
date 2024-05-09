package com.sugarweb.uims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 启动类
 *
 * @author xxd
 * @version 1.0
 */
@SpringBootApplication
@MapperScan({"com.sugarweb.uims.domain.repository","com.sugarweb.*.domain"})
public class UimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UimsApplication.class, args);
    }

}
