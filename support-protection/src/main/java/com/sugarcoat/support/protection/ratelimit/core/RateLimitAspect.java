package com.sugarcoat.support.protection.ratelimit.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.exception.RateLimitException;
import com.sugarcoat.api.exception.ServiceException;
import com.sugarcoat.api.protection.RateLimit;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.Set;

/**
 *
 * 限流注解 aop 拦截
 *
 * @Author lmh
 * @Description TODO 限流注解 aop 拦截
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
            // 判断模式

            String mark = rateLimit.mark();
            if (StrUtil.isBlank(mark)) {
                return;
            }

            RRateLimiter ra = client.getRateLimiter(PREFIX_REDIS_KEY + mark);
            log.info("get ra: {}", mark);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
