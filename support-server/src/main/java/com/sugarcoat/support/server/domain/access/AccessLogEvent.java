package com.sugarcoat.support.server.domain.access;

import org.springframework.context.ApplicationEvent;

/**
 * 访问日志事件
 *
 * @author xxd
 * @since 2022-11-21
 */
public class AccessLogEvent extends ApplicationEvent {

	public AccessLogEvent(AccessLog source) {
		super(source);
	}

}
