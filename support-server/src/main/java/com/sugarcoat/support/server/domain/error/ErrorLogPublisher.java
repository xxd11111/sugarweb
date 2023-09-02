package com.sugarcoat.support.server.domain.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 异常日志发布者
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/1
 */
@AllArgsConstructor
public class ErrorLogPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void publishEvent(ErrorLog errorLog) {
		applicationEventPublisher.publishEvent(new ErrorLogEvent(errorLog));
	}

	public void publishEvent(HttpServletRequest req, Throwable ex) {
		ErrorLog errorLog = new ErrorLog();
		// todo
		publishEvent(errorLog);
	}

}
