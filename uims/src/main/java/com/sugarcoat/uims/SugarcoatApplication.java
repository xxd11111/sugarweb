package com.sugarcoat.uims;

import com.sugarcoat.support.protection.ratelimit.core.EnableRateLimit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 *
 * @author xxd
 * @date 2022-10-09
 */
@EnableRateLimit
@SpringBootApplication
@EntityScan
@EnableJpaRepositories
public class SugarcoatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SugarcoatApplication.class, args);
    }

}
