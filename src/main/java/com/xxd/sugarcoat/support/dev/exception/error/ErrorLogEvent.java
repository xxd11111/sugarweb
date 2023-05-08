package com.xxd.sugarcoat.support.dev.exception.error;

import org.springframework.context.ApplicationEvent;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public class ErrorLogEvent extends ApplicationEvent{
    public ErrorLogEvent(Object source) {
        super(source);
    }
}
