package com.sugarcoat.support.server.exception;

import com.sugarcoat.protocol.HttpCode;
import com.sugarcoat.protocol.Result;
import com.sugarcoat.protocol.exception.*;
import com.sugarcoat.protocol.exception.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxd
 * @description 全局异常拦截（只做基础异常拦截，其余自行实现）
 * @date 2022-10-27
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private ExceptionLogService logClient;

    @Autowired
    public void setLogClient(ExceptionLogService logClient) {
        if (logClient instanceof DefaultExceptionLogService) {
            log.warn("=====>未实现ExceptionLogClient,当前忽略异常信息记录");
        }
        this.logClient = logClient;
    }

    @ExceptionHandler(FrameworkException.class)
    public Result<?> frameworkException(HttpServletRequest req, Throwable ex) {
        log.error("[FrameworkException]", ex);
        //日志插入
        logClient.sendExceptionLog(req, ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    public Result<?> securityException(HttpServletRequest req, Throwable ex) {
        log.error("[SecurityException]", ex);
        //日志插入
        logClient.sendExceptionLog(req, ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServerException.class)
    public Result<?> serverException(HttpServletRequest req, Throwable ex) {
        log.error("[ServerException]", ex);
        //日志插入
        logClient.sendExceptionLog(req, ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceException(HttpServletRequest req, Throwable ex) {
        log.error("[ServiceException]", ex);
        //日志插入
        logClient.sendExceptionLog(req, ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateException.class)
    public Result<?> validateException(HttpServletRequest req, Throwable ex) {
        log.error("[ValidateException]", ex);
        //日志插入
        logClient.sendExceptionLog(req, ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[Exception]", ex);
        //日志插入
        logClient.sendExceptionLog(req, ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

}
