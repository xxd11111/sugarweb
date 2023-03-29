package com.xxd.sugarcoat.support.dev.log.access;

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
    private AccessLogService accessLogService;

    @Override
    public void onApplicationEvent(AccessLogEvent event) {
        //accessLogService.saveLog(event);
    }
}
