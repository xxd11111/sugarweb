package com.sugarcoat.support.server.access;

import com.sugarcoat.support.server.api.AccessLogPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
@Component
public class AccessLogListener implements ApplicationListener<AccessLogEvent> {

    @Resource
    @Lazy
    private AccessLogPublisher accessLogPublisher;

    @Override
    public void onApplicationEvent(AccessLogEvent event) {
        //accessLogService.saveLog(event);
    }
}
