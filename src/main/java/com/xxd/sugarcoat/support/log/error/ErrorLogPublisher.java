package com.xxd.sugarcoat.support.log.error;

import com.xxd.sugarcoat.support.log.access.AccessLogEvent;
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
