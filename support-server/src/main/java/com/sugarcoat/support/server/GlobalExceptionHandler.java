package com.sugarcoat.support.server;

import com.sugarcoat.protocol.common.HttpCode;
import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.protocol.exception.SecurityException;
import com.sugarcoat.protocol.exception.*;
import com.sugarcoat.support.server.domain.ApiLogInfoHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(IdempotentException.class)
    public Result<?> idempotentException(HttpServletRequest req, Throwable ex) {
        log.error("[IdempotentException]", ex);
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(RateLimitException.class)
    public Result<?> rateLimitException(HttpServletRequest req, Throwable ex) {
        log.error("[RateLimitException]", ex);
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(FrameworkException.class)
    public Result<?> frameworkException(HttpServletRequest req, Throwable ex) {
        log.error("[FrameworkException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    public Result<?> securityException(HttpServletRequest req, Throwable ex) {
        log.error("[SecurityException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServerException.class)
    public Result<?> serverException(HttpServletRequest req, Throwable ex) {
        log.error("[ServerException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceException(HttpServletRequest req, Throwable ex) {
        log.error("[ServiceException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateException.class)
    public Result<?> validateException(HttpServletRequest req, Throwable ex) {
        log.error("[ValidateException]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[Exception]", ex);
        return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

}
