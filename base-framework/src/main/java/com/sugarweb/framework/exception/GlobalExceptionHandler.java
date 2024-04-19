package com.sugarweb.framework.exception;

import com.sugarweb.framework.common.HttpCode;
import com.sugarweb.framework.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 *
 * @author xxd
 * @version 1.0
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FrameworkException.class)
    public R<?> frameworkException(Throwable ex) {
        log.error("[FrameworkException]", ex);
        return R.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    public R<?> securityException(Throwable ex) {
        log.error("[SecurityException]", ex);
        return R.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServerException.class)
    public R<?> serverException(Throwable ex) {
        log.error("[ServerException]", ex);
        return R.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public R<?> serviceException(Throwable ex) {
        log.error("[ServiceException]", ex);
        return R.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateException.class)
    public R<?> validateException(Throwable ex) {
        log.error("[ValidateException]", ex);
        return R.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public R<?> exceptionHandler(Throwable ex) {
        log.error("[Exception]", ex);
        return R.error(HttpCode.INTERNAL_SERVER_ERROR);
    }

}
