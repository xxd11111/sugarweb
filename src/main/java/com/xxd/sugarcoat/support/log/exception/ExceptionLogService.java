package com.xxd.sugarcoat.support.log.exception;

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
    void sendErrorLog(HttpServletRequest req, Throwable ex);

    /**
     * 发送安全日志
     * @param req 请求
     * @param ex 异常
     */
    void sendSecurityLog(HttpServletRequest req, Throwable ex);
}
