package com.sugarcoat.support.server.domain.access;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 访问日志发布者
 *
 * @author xxd
 * @date 2022-11-21
 */
@AllArgsConstructor
public class AccessLogPublisher {

	private final AccessLogRepository accessLogRepository;

	public void publishEvent() {
		AccessLog accessLog = new AccessLog();
		accessLogRepository.save(accessLog);
	}

}
