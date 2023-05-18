package com.sugarcoat.support.server.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxd
 * @description 异常日志客户端
 * @date 2022-11-15
 */
public interface ExceptionLogService {
    /**
     * 发送异常日志
     * @param req 请求
     * @param ex 异常
     */
    void sendExceptionLog(HttpServletRequest req, Throwable ex);

}
