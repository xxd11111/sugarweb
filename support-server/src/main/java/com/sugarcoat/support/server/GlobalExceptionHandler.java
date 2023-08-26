package com.sugarcoat.support.server;

import com.sugarcoat.api.common.HttpCode;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.api.exception.SecurityException;
import com.sugarcoat.api.exception.*;
import com.sugarcoat.support.server.domain.error.ErrorLogPublisher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xxd
 * @description 全局异常拦截
 * @date 2022-10-27
 */

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

	private final ErrorLogPublisher errorLogPublisher;

	@ExceptionHandler(IdempotentException.class)
	public Result<?> idempotentException(HttpServletRequest req, Throwable ex) {
		log.error("[IdempotentException]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(ex.getMessage());
	}

	@ExceptionHandler(RateLimitException.class)
	public Result<?> rateLimitException(HttpServletRequest req, Throwable ex) {
		log.error("[RateLimitException]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(ex.getMessage());
	}

	@ExceptionHandler(FrameworkException.class)
	public Result<?> frameworkException(HttpServletRequest req, Throwable ex) {
		log.error("[FrameworkException]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SecurityException.class)
	public Result<?> securityException(HttpServletRequest req, Throwable ex) {
		log.error("[SecurityException]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ServerException.class)
	public Result<?> serverException(HttpServletRequest req, Throwable ex) {
		log.error("[ServerException]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ServiceException.class)
	public Result<?> serviceException(HttpServletRequest req, Throwable ex) {
		log.error("[ServiceException]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ValidateException.class)
	public Result<?> validateException(HttpServletRequest req, Throwable ex) {
		log.error("[ValidateException]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public Result<?> exceptionHandler(HttpServletRequest req, Throwable ex) {
		log.error("[Exception]", ex);
		// 日志插入
		errorLogPublisher.publishEvent(req, ex);
		return Result.error(HttpCode.INTERNAL_SERVER_ERROR);
	}

}
