package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.JsonUtil;
import com.sugarcoat.protocol.ServletUtil;
import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.protocol.security.SecurityHelper;
import com.sugarcoat.protocol.server.ApiLog;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口日志拦截
 *
 * @author 许向东
 * @date 2023/9/27
 */
@Aspect
public class ApiLogAspect {

    private SgcApiCallLogRepository apiCallLogRepository;

    private SgcApiErrorLogRepository apiErrorLogRepository;

    @Around("@annotation(apiLog)")
    public Object apiLog(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable {
        LocalDateTime start = LocalDateTime.now();
        try {
            Object result = joinPoint.proceed();
            SgcApiCallLog sgcApiCallLog = new SgcApiCallLog();

            sgcApiCallLog.setApiId("");
            sgcApiCallLog.setApiName("");
            sgcApiCallLog.setAppIp("");
            sgcApiCallLog.setAppPort("");

            HttpServletRequest request = ServletUtil.getRequest();
            if (request != null) {
                sgcApiCallLog.setRequestMethod(request.getMethod());
                sgcApiCallLog.setRequestUrl(request.getRequestURI());
                sgcApiCallLog.setRequestIp(request.getRemoteAddr());
                sgcApiCallLog.setRequestUserAgent(request.getHeader("User-Agent"));
                sgcApiCallLog.setRequestDate(start);
            }

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String[] argNames = methodSignature.getParameterNames();
            Object[] argValues = joinPoint.getArgs();
            // 拼接参数
            Map<String, Object> args = new HashMap<>(argValues.length);
            for (int i = 0; i < argNames.length; i++) {
                String argName = argNames[i];
                Object argValue = argValues[i];
                args.put(argName, argValue);
            }
            sgcApiCallLog.setRequestParams(JsonUtil.toJsonStr(args));

            sgcApiCallLog.setUserId(SecurityHelper.currentUserId());
            sgcApiCallLog.setUsername(SecurityHelper.getUsername());

            if (result instanceof Result<?> r) {
                sgcApiCallLog.setResultCode(r.getCode());
                sgcApiCallLog.setResultData(JsonUtil.toJsonStr(r.getData()));
                sgcApiCallLog.setResultMsg(r.getMsg());
            }
            LocalDateTime end = LocalDateTime.now();
            Duration between = Duration.between(start, end);
            long duration = between.toMillis();
            sgcApiCallLog.setDuration((int) duration);

            apiCallLogRepository.save(sgcApiCallLog);
            return result;
        } catch (Throwable e) {
            SgcApiErrorLog sgcApiErrorLog = new SgcApiErrorLog();
            apiErrorLogRepository.save(sgcApiErrorLog);
            throw e;
        }
    }


}
