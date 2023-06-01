package com.sugarcoat.support.server.log;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常日志发布者
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/1
 */
@AllArgsConstructor
public class ErrorLogPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(ErrorLogEvent errorLogEvent) {
        applicationEventPublisher.publishEvent(errorLogEvent);
    }

    public void publishEvent(HttpServletRequest req, Throwable ex) {

    }
}
