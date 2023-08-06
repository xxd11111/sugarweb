package com.sugarcoat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 启动类
 *
 * @author xxd
 * @date 2022-10-09
 */
@SpringBootApplication
@EntityScan
public class SugarcoatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SugarcoatApplication.class, args);
    }

}
