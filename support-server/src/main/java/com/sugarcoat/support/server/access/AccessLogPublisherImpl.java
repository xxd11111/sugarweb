package com.sugarcoat.support.server.access;

import com.sugarcoat.support.server.api.AccessLogPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
@Component
public class AccessLogPublisherImpl implements AccessLogPublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void publisherEvent(AccessLogEvent accessLogEvent){
        applicationEventPublisher.publishEvent(accessLogEvent);
    }

    @Override
    public void publisherEvent(HttpServletRequest req) {

    }
}
