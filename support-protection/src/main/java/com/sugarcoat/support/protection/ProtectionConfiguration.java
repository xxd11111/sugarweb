package com.sugarcoat.support.protection;


import com.sugarcoat.protocol.protection.IdempotentKeyGenerator;
import com.sugarcoat.support.protection.idempotent.DefaultKeyGenerator;
import com.sugarcoat.support.protection.idempotent.IdempotentAspect;
import com.sugarcoat.support.protection.ratelimit.RateLimitAspect;
import com.sugarcoat.support.protection.ratelimit.RateLimitConfigConditionInject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 限流自动装配
 *
 * @author lmh
 * @CreateTime 2023-08-23 15:31
 */
@Slf4j
@Configuration
public class ProtectionConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public IdempotentKeyGenerator idempotentKeyGenerator() {
        return new DefaultKeyGenerator();
    }

    @Bean
    public IdempotentAspect idempotentAspect(IdempotentKeyGenerator idempotentKeyGenerator, RedissonClient redissonClient) {
        return new IdempotentAspect(idempotentKeyGenerator, redissonClient);
    }

    @Bean
    @Conditional(value = RateLimitConfigConditionInject.class)
    public RateLimitAspect rateLimitAspect(RedissonClient client) {
        return new RateLimitAspect(client);
    }

    @Bean
    public RateLimitConfigConditionInject rateLimitConfigConditionInject() {
        return new RateLimitConfigConditionInject();
    }
}
