package com.sugarweb.server.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.R;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.framework.security.LoginUser;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.framework.utils.JsonUtil;
import com.sugarweb.framework.utils.ServletUtil;
import com.sugarweb.server.po.ApiCallLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 接口日志拦截
 *
 * @author 许向东
 * @version 1.0
 */
@Aspect
@Slf4j
public class ApiLogAspect {

    @Around("@annotation(apiLog)")
    public Object apiLog(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable {
        Object result = null;
        LocalDateTime start = LocalDateTime.now();
        try {
            //执行方法
            result = joinPoint.proceed();
            ApiCallLog apiCallLog = buildApiCallLog(apiLog, joinPoint, start, result, null);
            Db.save(apiCallLog);
            return result;
        } catch (Throwable ex) {
            ApiCallLog apiCallLog = buildApiCallLog(apiLog, joinPoint, start, result, ex);
            Db.save(apiCallLog);
            throw ex;
        }

    }

    @SneakyThrows(FrameworkException.class)
    public void loadHttpInfo(ApiCallLog apiCallLog, LocalDateTime start) {
        HttpServletRequest request = ServletUtil.getRequest();
        apiCallLog.setRequestMethod(request.getMethod());
        String requestURI = request.getRequestURI();
        apiCallLog.setRequestUrl(requestURI);
        apiCallLog.setRequestIp(ServletUtil.getRequestIp());
        apiCallLog.setUserAgent(request.getHeader("User-Agent"));

        apiCallLog.setStartTime(start);
        LocalDateTime now = LocalDateTime.now();
        apiCallLog.setEndTime(now);
        apiCallLog.setCostTime(now.getNano() - start.getNano());
    }


    @SneakyThrows(FrameworkException.class)
    public void loadParamsInfo(ApiCallLog apiCallLog, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = joinPoint.getArgs();
        // 拼接参数
        Map<String, Object> args = new HashMap<>(argValues.length);
        for (int i = 0; i < argNames.length; i++) {
            String argName = argNames[i];
            Object argValue = argValues[i];
            if (!isIgnoreArgs(argValue)) {
                args.put(argName, argValue);
            }
        }
        //设置的是java方法的参数
        apiCallLog.setMethodParams(JsonUtil.toJsonStr(args));
    }

    private static boolean isIgnoreArgs(Object object) {
        Class<?> clazz = object.getClass();
        // 处理数组的情况
        if (clazz.isArray()) {
            return IntStream.range(0, Array.getLength(object))
                    .anyMatch(index -> isIgnoreArgs(Array.get(object, index)));
        }
        // 递归，处理数组、Collection、Map 的情况
        if (Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) object).stream()
                    .anyMatch(ApiLogAspect::isIgnoreArgs);
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return isIgnoreArgs(((Map<?, ?>) object).values());
        }
        // obj
        return object instanceof MultipartFile
                || object instanceof HttpServletRequest
                || object instanceof HttpServletResponse
                || object instanceof BindingResult;
    }

    @SneakyThrows(FrameworkException.class)
    public void loadResultInfo(ApiCallLog apiCallLog, Object result, LocalDateTime start) {
        if (result instanceof R<?> r) {
            apiCallLog.setResultCode(r.getCode());
            apiCallLog.setResultData(JsonUtil.toJsonStr(r.getData()));
            apiCallLog.setResultMessage(r.getMessage());
        }
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);
        long duration = between.toMillis();
        apiCallLog.setCostTime((int) duration);
    }

    @SneakyThrows(FrameworkException.class)
    public void loadUserInfo(ApiCallLog apiCallLog) {
        LoginUser loginUser = SecurityHelper.getLoginUser();
        String userId = loginUser.getUserId();
        String username = loginUser.getUsername();
        apiCallLog.setUserId(userId);
        apiCallLog.setUsername(username);
    }

    @SneakyThrows(FrameworkException.class)
    public void loadExceptionInfo(ApiCallLog apiCallLog, Throwable ex) {
        if (ex == null) {
            return;
        }
        apiCallLog.setExceptionClassName(ex.getClass().getName());
        apiCallLog.setExceptionMessage(ExceptionUtil.getMessage(ex));
        apiCallLog.setExceptionStackTrace(ExceptionUtil.stacktraceToString(ex, 500));
    }

    public ApiCallLog buildApiCallLog(ApiLog apiLog, ProceedingJoinPoint joinPoint, LocalDateTime start, Object result, Throwable throwable) {
        ApiCallLog apiCallLog = new ApiCallLog();
        try {
            apiCallLog.setApiName(apiLog.value());

            //加载网络请求的参数
            loadHttpInfo(apiCallLog, start);

            //加载执行方法的参数
            loadParamsInfo(apiCallLog, joinPoint);

            //加载用户信息
            loadUserInfo(apiCallLog);

            //加载结果信息
            loadResultInfo(apiCallLog, result, start);

            //加载异常信息
            loadExceptionInfo(apiCallLog, throwable);

        } catch (Throwable ex) {
            log.error("apiCallLog功能异常，apiLog:{}, joinPoint:{}, start:{}, result:{}", apiLog, joinPoint, start, result, ex);
        }
        return apiCallLog;
    }

}
