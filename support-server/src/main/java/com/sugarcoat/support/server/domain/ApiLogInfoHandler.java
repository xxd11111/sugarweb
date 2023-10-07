package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.JsonUtil;
import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.protocol.security.SecurityHelper;
import com.sugarcoat.protocol.security.UserInfo;
import com.sugarcoat.protocol.server.ApiCallLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * api日志信息处理 解决跨方法传递参数问题
 *
 * @author 许向东
 * @date 2023/9/22
 */
public class ApiLogInfoHandler {

    public static void loadRequestInfo(SgcApiCallLog apiCallLog, HttpServletRequest request, LocalDateTime start) {
        apiCallLog.setRequestMethod(request.getMethod());
        apiCallLog.setRequestUrl(request.getRequestURI());
        apiCallLog.setRequestIp(request.getRemoteAddr());
        apiCallLog.setRequestUserAgent(request.getHeader("User-Agent"));
        apiCallLog.setRequestTime(start);
    }

    public static void loadParamsInfo(SgcApiCallLog apiCallLog, ProceedingJoinPoint joinPoint) {
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
        //设置的是java方法的参数
        apiCallLog.setRequestParams(JsonUtil.toJsonStr(args));
    }

    public static void loadResultInfo(SgcApiCallLog apiCallLog, Object result, LocalDateTime start) {
        if (result instanceof Result<?> r) {
            apiCallLog.setResultCode(r.getCode());
            apiCallLog.setResultData(JsonUtil.toJsonStr(r.getData()));
            apiCallLog.setResultMsg(r.getMsg());
        }
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);
        long duration = between.toMillis();
        apiCallLog.setDuration((int) duration);
    }

    public static void loadUserInfo(SgcApiCallLog apiCallLog) {
        UserInfo userInfo = SecurityHelper.currentAccount();
        String userId = userInfo.getId();
        String username = userInfo.getUsername();
        apiCallLog.setUserId(userId);
        apiCallLog.setUsername(username);
    }

    public static void loadErrorInfo(SgcApiErrorLog apiErrorLog, Throwable ex) {
        apiErrorLog.setExceptionTime(LocalDateTime.now());
        apiErrorLog.setExceptionMessage(ex.getMessage());

        StackTraceElement[] stackTrace = ex.getStackTrace();
        apiErrorLog.setExceptionFileName(stackTrace[0].getFileName());
        apiErrorLog.setExceptionClassName(stackTrace[0].getClassName());
        apiErrorLog.setExceptionMethodName(stackTrace[0].getMethodName());
        apiErrorLog.setExceptionLineNumber(stackTrace[0].getLineNumber());

        Class<? extends Throwable> exClass = ex.getClass();
        apiErrorLog.setExceptionName(exClass.getName());

        // apiErrorLog.setExceptionRootCauseMessage();
        // apiErrorLog.setExceptionStackTrace();
    }

    public static void sendLog(){

    }

    public static void sendErrorLog(){

    }

}
