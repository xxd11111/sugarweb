package com.xxd.sugarcoat.support.dev.exception.error;

import com.xxd.sugarcoat.support.dev.server.access.AccessLogEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public class ErrorLogPublisher {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void publisherEvent(AccessLogEvent accessLogEvent){
        applicationEventPublisher.publishEvent(accessLogEvent);
    }
}
