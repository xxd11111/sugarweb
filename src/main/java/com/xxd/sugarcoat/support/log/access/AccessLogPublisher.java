package com.xxd.sugarcoat.support.log.access;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
@Component
public class AccessLogPublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void publisherEvent(AccessLogEvent accessLogEvent){
        applicationEventPublisher.publishEvent(accessLogEvent);
    }
}
