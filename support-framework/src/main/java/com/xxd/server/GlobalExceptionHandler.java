package com.xxd.server;

import com.sugarcoat.protocol.common.HttpCode;
import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.protocol.exception.SecurityException;
import com.sugarcoat.protocol.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 *
 * @author xxd
 * @since 2022-10-27
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FrameworkException.class)
    public Result<?> frameworkException(Throwable ex) {
        log.error("[FrameworkException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    public Result<?> securityException(Throwable ex) {
        log.error("[SecurityException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServerException.class)
    public Result<?> serverException(Throwable ex) {
        log.error("[ServerException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceException(Throwable ex) {
        log.error("[ServiceException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateException.class)
    public Result<?> validateException(Throwable ex) {
        log.error("[ValidateException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Throwable ex) {
        log.error("[Exception]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

}
