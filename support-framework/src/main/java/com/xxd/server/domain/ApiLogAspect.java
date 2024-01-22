package com.xxd.server.domain;

import com.sugarcoat.protocol.server.ApiLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.time.LocalDateTime;

/**
 * 接口日志拦截
 *
 * @author 许向东
 * @date 2023/9/27
 */
@Aspect
public record ApiLogAspect(ApiLogInfoHandler apiLogInfoHandler) {

    @Around("@annotation(apiLog)")
    public Object apiLog(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable {
        Object result;
        LocalDateTime start = LocalDateTime.now();
        try {
            //执行方法
            result = joinPoint.proceed();
            apiLogInfoHandler.sendLog(apiLog, joinPoint, start, result);
            return result;
        } catch (Throwable ex) {
            apiLogInfoHandler.sendErrorLog(apiLog, joinPoint, start, ex);
            throw ex;
        }
    }

}
