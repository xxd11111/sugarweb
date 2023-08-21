package com.sugarcoat.support.protection;


import com.sugarcoat.api.protection.IdempotentKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
public class ProtectionConfig {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public IdempotentKeyGenerator idempotentKeyGenerator() {
        return new DefaultKeyGenerator();
    }

    @Bean
    public IdempotentAspect idempotentAspect(RedisTemplate<String, Object> redisTemplate, IdempotentKeyGenerator idempotentKeyGenerator) {
        return new IdempotentAspect(redisTemplate, idempotentKeyGenerator);
    }

}
