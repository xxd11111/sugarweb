package com.sugarcoat.uims;

import com.xxd.BeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 *
 * @author xxd
 * @since 2022-10-09
 */
@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties
public class SugarcoatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SugarcoatApplication.class, args);
    }

    //todo
    @Bean
    public BeanUtil beanUtil(){
        return new BeanUtil();
    }

}
