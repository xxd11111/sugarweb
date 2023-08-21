package com.sugarcoat.support.protection;


import com.sugarcoat.support.cache.RedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
public class ProtectionConfig {

    @Bean
    public IdempotentKeyGenerator idempotentKeyGenerator() {
        return new DefaultKeyGenerator();
    }

    @Bean
    public IdempotentAspect idempotentAspect(RedisTemplate<String, Object> redisTemplate, IdempotentKeyGenerator idempotentKeyGenerator) {
        return new IdempotentAspect(redisTemplate, idempotentKeyGenerator);
    }

}
