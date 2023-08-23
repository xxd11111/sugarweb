package com.sugarcoat.support.protection.ratelimit;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.exception.ServiceException;
import com.sugarcoat.api.protection.RateLimit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

/**
 *
 * todo 限流注解 aop 拦截
 *
 * @Author lmh
 * @Description
 * @CreateTime 2023-08-23 15:31
 */

@Slf4j
@Aspect
@RequiredArgsConstructor
public class RateLimitAspect implements InitializingBean {

    public static final String REDIS_KEY = "ratelimit:";

    private final RedisTemplate<String, Object> redisTemplate;
    @Before("@annotation(rateLimit)")
    public void aspect(JoinPoint point, RateLimit rateLimit) {
        String mark = rateLimit.mark();
        if (StrUtil.isBlank(mark)) {
            return;
        }

        String fullName = REDIS_KEY + mark;

        Long increment = redisTemplate.opsForValue().increment(fullName);
        Assert.notNull(increment, "error redis increment value");

        Object o = redisTemplate.opsForValue().get(fullName);
        if (ObjUtil.isNull(o)) {
            redisTemplate.opsForValue().setIfAbsent(fullName, increment, rateLimit.frequencyTime(), rateLimit.frequencyTimeUnit());
            return;
        }

        if (increment > Long.valueOf(rateLimit.frequency())) {
            throw new ServiceException();
        }

        redisTemplate.opsForValue().set(fullName, increment, rateLimit.frequencyTime(), rateLimit.frequencyTimeUnit());

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisTemplate.delete(REDIS_KEY + "*");
    }
}
