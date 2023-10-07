package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.ServletUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.server.ApiLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ApiLogAspect {

    private final SgcApiCallLogRepository apiCallLogRepository;

    private final SgcApiErrorLogRepository apiErrorLogRepository;

    @Around("@annotation(apiLog)")
    public Object apiLog(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable {
        LocalDateTime start = LocalDateTime.now();
        SgcApiErrorLog sgcApiCallLog = new SgcApiErrorLog();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            ApiLogInfoHandler.loadErrorInfo(sgcApiCallLog, ex);
            apiErrorLogRepository.save(sgcApiCallLog);
            throw ex;
        }

        try {
            //启用接口记录时，需要存在注解 @ApiTags, 若不存在，识别@RequestMapping
            sgcApiCallLog.setApiId("");
            sgcApiCallLog.setApiName("");
            sgcApiCallLog.setAppIp("");
            sgcApiCallLog.setAppPort("");

            HttpServletRequest request = ServletUtil.getRequest();
            if (request != null) {
                ApiLogInfoHandler.loadResultInfo(sgcApiCallLog, request, start);
            }

            //设置的是java方法的参数
            ApiLogInfoHandler.loadParamsInfo(sgcApiCallLog, joinPoint);

            ApiLogInfoHandler.loadUserInfo(sgcApiCallLog);

            ApiLogInfoHandler.loadResultInfo(sgcApiCallLog, result, start);

            apiCallLogRepository.save(sgcApiCallLog);
        } catch (Throwable ex) {
            throw new FrameworkException("日志bug");
        }

        return result;
    }


}
