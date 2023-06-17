package com.sugarcoat.support.server.log.access;

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

	private final ApplicationEventPublisher applicationEventPublisher;

	public void publishEvent(AccessLogEvent accessLogEvent) {
		applicationEventPublisher.publishEvent(accessLogEvent);
	}

}
