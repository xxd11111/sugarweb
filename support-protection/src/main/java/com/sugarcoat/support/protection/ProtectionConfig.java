package com.sugarcoat.support.protection;


import com.sugarcoat.api.protection.IdempotentKeyGenerator;
import com.sugarcoat.support.protection.idempotent.DefaultKeyGenerator;
import com.sugarcoat.support.protection.idempotent.IdempotentAspect;
import com.sugarcoat.support.protection.ratelimit.RateLimitAspect;
import com.sugarcoat.support.protection.ratelimit.RateLimitConfigConditionInject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
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

    @Bean
    @Conditional(value = RateLimitConfigConditionInject.class)
    public RateLimitAspect rateLimitAspect(RedisTemplate<String, Object> redisTemplate) {
        return new RateLimitAspect(redisTemplate);
    }

    @Bean
    public RateLimitConfigConditionInject rateLimitConfigConditionInject() {
        return new RateLimitConfigConditionInject();
    }
}
