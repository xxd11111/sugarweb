package com.xxd.sugarcoat.support.dev.exception.exception;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxd
 * @description 默认日志客户端
 * @date 2022-11-21
 */
@Component
public class NoneExceptionLogService implements ExceptionLogService {
    @Override
    public void sendErrorLog(HttpServletRequest req, Throwable ex) {
        //ignore
    }

    @Override
    public void sendSecurityLog(HttpServletRequest req, Throwable ex) {
        //ignore
    }
}
