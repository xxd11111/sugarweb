package com.sugarcoat.support.protection.idempotent;

import com.sugarcoat.api.exception.IdempotentException;
import com.sugarcoat.api.protection.Idempotent;
import com.sugarcoat.api.protection.IdempotentKeyGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class IdempotentAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    private final IdempotentKeyGenerator idempotentKeyGenerator;

    private final RedissonClient client;

    private static final String REDIS_LOCK = "idempotent:redis-lock";

    /**
     * 前置通知
     * 1. 拿到方法入参和路径，并解析成幂等redisKey
     * 2. 使用redis的 setIfAbsent方法，判断key是否存在
     * 3. 如果存在则抛出异常
     *
     * @param point 切面入参
     * @param idempotent 注解
     */
    @Before("@annotation(idempotent)")
    public void aspect(JoinPoint point, Idempotent idempotent) {
        RLock lock = client.getLock(REDIS_LOCK);
        lock.lock();

        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Assert.notNull(requestAttributes, "error request context");
            HttpServletRequest request = requestAttributes.getRequest();
            String requestURI = request.getRequestURI();
            String generator = idempotentKeyGenerator.generator(requestURI, point.getArgs());

            Boolean flag = redisTemplate.opsForValue().setIfAbsent(generator, "", idempotent.expire(), idempotent.unit());
            if (Boolean.TRUE.equals(flag)) {
                return;
            }
            throw new Exception(idempotent.errorMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IdempotentException(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

}
