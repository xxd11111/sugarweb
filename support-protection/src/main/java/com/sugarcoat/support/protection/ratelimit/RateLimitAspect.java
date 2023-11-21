package com.sugarcoat.support.protection.ratelimit;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.protection.RateLimit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.*;

/**
 *
 * 限流注解 aop 拦截
 *
 * @author lmh
 * @CreateTime 2023-08-23 15:31
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class RateLimitAspect {

    public static final String PREFIX_REDIS_KEY = "ratelimit:";
    private static final String REDIS_LOCK = "ratelimit:redis-lock";

    private final RedissonClient client;

    @Before("@annotation(rateLimit)")
    public void aspect(JoinPoint point, RateLimit rateLimit) {
        // 使用redis锁
        RLock lock = client.getLock(REDIS_LOCK);
        lock.lock();
        try {
            String mark = rateLimit.mark();
            if (StrUtil.isBlank(mark)) {
                return;
            }

            RRateLimiter ra = client.getRateLimiter(PREFIX_REDIS_KEY + mark);
            ra.trySetRate(RateType.OVERALL, rateLimit.rate(), rateLimit.rateCycle(), RateIntervalUnit.SECONDS);
            //需要1个令牌
            if(ra.tryAcquire(1)){
                log.info("get ra: {}", mark);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
