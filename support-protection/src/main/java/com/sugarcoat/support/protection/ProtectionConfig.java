package com.sugarcoat.support.protection;


import com.sugarcoat.api.protection.IdempotentKeyGenerator;
import com.sugarcoat.support.protection.idempotent.DefaultKeyGenerator;
import com.sugarcoat.support.protection.idempotent.IdempotentAspect;
import com.sugarcoat.support.protection.ratelimit.core.RateLimitAspect;
import com.sugarcoat.support.protection.ratelimit.core.RateLimitConfigConditionInject;
import com.sugarcoat.support.protection.ratelimit.core.RateLimitScan;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * 限流自动装配
 *
 * @Author lmh
 * @Description 限流自动装配
 * @CreateTime 2023-08-23 15:31
 */
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
    public IdempotentAspect idempotentAspect(RedisTemplate<String, Object> redisTemplate, IdempotentKeyGenerator idempotentKeyGenerator, RedissonClient redissonClient) {
        return new IdempotentAspect(redisTemplate, idempotentKeyGenerator, redissonClient);
    }

    @Bean
    @Conditional(value = RateLimitConfigConditionInject.class)
    public RateLimitAspect rateLimitAspect(RedissonClient client) {
        return new RateLimitAspect(client);
    }

    @Bean
    public RateLimitScan rateLimitScan() {
        return new RateLimitScan();
    }

    @Bean
    public RateLimitConfigConditionInject rateLimitConfigConditionInject() {
        return new RateLimitConfigConditionInject();
    }
}
