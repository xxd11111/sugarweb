package com.sugarcoat.support.server.log.error;

import org.springframework.context.ApplicationEvent;

/**
 * 异常日志事件
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/1
 */
public class ErrorLogEvent extends ApplicationEvent {
    public ErrorLogEvent(ErrorLog source) {
        super(source);
    }
}
