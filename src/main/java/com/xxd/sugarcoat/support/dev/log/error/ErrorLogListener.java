package com.xxd.sugarcoat.support.dev.log.error;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public class ErrorLogListener implements ApplicationListener<ErrorLogEvent> {
    @Resource
    @Lazy
    private ErrorLogService errorLogService;

    @Override
    public void onApplicationEvent(ErrorLogEvent event) {
        //errorLogService.saveErrorLog(event);
    }
}
