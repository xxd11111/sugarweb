package com.xxd.server.domain;

import com.google.common.base.Strings;
import com.xxd.server.JsonUtil;
import com.xxd.server.ServletUtil;
import com.xxd.common.Result;
import com.xxd.exception.FrameworkException;
import com.xxd.server.ApiInfo;
import com.xxd.server.ApiLog;
import com.xxd.server.ApiManager;
import com.xxd.security.SecurityHelper;
import com.xxd.security.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * api日志信息处理
 *
 * @author 许向东
 * @date 2023/9/22
 */
@Slf4j
public record ApiLogInfoHandler(SgcApiCallLogRepository apiCallLogRepository,
                                SgcApiErrorLogRepository apiErrorLogRepository,
                                ApiManager apiManager) {

    @SneakyThrows(FrameworkException.class)
    public void loadRequestInfo(SgcApiCallLog apiCallLog, LocalDateTime start) {
        HttpServletRequest request = ServletUtil.getRequest();
        if (request == null) {
            return;
        }
        apiCallLog.setRequestMethod(request.getMethod());
        String requestURI = request.getRequestURI();
        apiCallLog.setRequestUrl(requestURI);
        apiCallLog.setRequestIp(request.getRemoteAddr());
        apiCallLog.setRequestUserAgent(request.getHeader("User-Agent"));
        apiCallLog.setRequestTime(start);

        ApiInfo apiInfo = apiManager.findApiByUrl(requestURI)
                //正常情况一定不为null
                .orElseThrow(() -> new FrameworkException("未加载到ApiInfo"));
        apiCallLog.setApiId(apiInfo.getOperationId());
        apiCallLog.setApiName(apiInfo.getSummary());
    }

    @SneakyThrows(FrameworkException.class)
    public void loadParamsInfo(SgcApiCallLog apiCallLog, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = joinPoint.getArgs();
        // 拼接参数
        Map<String, Object> args = new HashMap<>(argValues.length);
        for (int i = 0; i < argNames.length; i++) {
            String argName = argNames[i];
            Object argValue = argValues[i];
            if(!isIgnoreArgs(argValue)){
                args.put(argName, argValue);
            }
        }
        //设置的是java方法的参数
        apiCallLog.setRequestParams(JsonUtil.toJsonStr(args));
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
                    .anyMatch((Predicate<Object>) ApiLogInfoHandler::isIgnoreArgs);
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
    public void loadResultInfo(SgcApiCallLog apiCallLog, Object result, LocalDateTime start) {
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

    @SneakyThrows(FrameworkException.class)
    public void loadUserInfo(SgcApiCallLog apiCallLog) {
        UserInfo userInfo = SecurityHelper.getUserInfo();
        String userId = userInfo.getId();
        String username = userInfo.getUsername();
        apiCallLog.setUserId(userId);
        apiCallLog.setUsername(username);
    }

    @SneakyThrows(FrameworkException.class)
    public void loadExceptionInfo(SgcApiErrorLog apiErrorLog, Throwable ex) {
        apiErrorLog.setExceptionTime(LocalDateTime.now());
        //异常名
        apiErrorLog.setExceptionName(ex.getClass().getName());
        //异常信息
        String exceptionMessage = Strings.lenientFormat("%s: %s", ex.getClass().getSimpleName(), ex.getMessage());
        apiErrorLog.setExceptionMessage(exceptionMessage);
        //异常根信息
        apiErrorLog.setExceptionRootCauseMessage(getRootCauseMessage(ex));

        //首行栈信息
        StackTraceElement[] stackTrace = ex.getStackTrace();
        apiErrorLog.setExceptionFileName(stackTrace[0].getFileName());
        apiErrorLog.setExceptionClassName(stackTrace[0].getClassName());
        apiErrorLog.setExceptionMethodName(stackTrace[0].getMethodName());
        apiErrorLog.setExceptionLineNumber(stackTrace[0].getLineNumber());

        //异常链路信息
        apiErrorLog.setExceptionStackTrace(getExceptionStackTrace(ex));
    }

    private String getRootCauseMessage(Throwable ex) {
        List<Throwable> list;
        for (list = new ArrayList<>(); ex != null && !list.contains(ex); ex = ex.getCause()) {
            list.add(ex);
        }
        Throwable rootEx = list.size() < 1 ? null : (Throwable) list.get(list.size() - 1);
        return null == rootEx ? "null" : Strings.lenientFormat("%s: %s", rootEx.getClass().getSimpleName(), rootEx.getMessage());
    }

    private String getExceptionStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    @SneakyThrows(FrameworkException.class)
    public void saveLog(SgcApiCallLog apiCallLog) {
        apiCallLogRepository.save(apiCallLog);
    }

    @SneakyThrows(FrameworkException.class)
    public void saveErrorLog(SgcApiErrorLog apiErrorLog) {
        apiErrorLogRepository.save(apiErrorLog);
    }

    public void sendLog(ApiLog apiLog, ProceedingJoinPoint joinPoint, LocalDateTime start, Object result) {
        if (!apiLog.enable()) {
            return;
        }
        try {
            SgcApiCallLog sgcApiCallLog = new SgcApiCallLog();
            //加载网络请求的参数
            loadRequestInfo(sgcApiCallLog, start);

            //加载执行方法的参数
            loadParamsInfo(sgcApiCallLog, joinPoint);

            //加载用户信息
            loadUserInfo(sgcApiCallLog);

            //加载结果信息
            loadResultInfo(sgcApiCallLog, result, start);

            //保存结果
            saveLog(sgcApiCallLog);
        } catch (Throwable ex) {
            log.error("sendLog功能异常，apiLog:{}, joinPoint:{}, start:{}, result:{}", apiLog, joinPoint, start, result, ex);
        }


    }

    public void sendErrorLog(ApiLog apiLog, ProceedingJoinPoint joinPoint, LocalDateTime start, Throwable exception) {
        if (!apiLog.enable()) {
            return;
        }
        try {
            SgcApiErrorLog sgcApiErrorLog = new SgcApiErrorLog();
            //加载网络请求的参数
            loadRequestInfo(sgcApiErrorLog, start);

            //加载执行方法的参数
            loadParamsInfo(sgcApiErrorLog, joinPoint);

            //加载用户信息
            loadUserInfo(sgcApiErrorLog);

            //加载异常信息
            loadExceptionInfo(sgcApiErrorLog, exception);

            //保存结果
            saveErrorLog(sgcApiErrorLog);
        } catch (Throwable ex) {
            log.error("sendErrorLog功能异常，apiLog:{}, joinPoint:{}, start:{}, exception:{}", apiLog, joinPoint, start, exception, ex);
        }
    }


}
