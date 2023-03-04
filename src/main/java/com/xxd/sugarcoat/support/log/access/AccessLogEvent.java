package com.xxd.sugarcoat.support.log.access;

import org.springframework.context.ApplicationEvent;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public class AccessLogEvent extends ApplicationEvent {

    public AccessLogEvent(Object source) {
        super(source);
    }
}
