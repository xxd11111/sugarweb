package com.sugarcoat.support.server.api;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxd
 * @description 访问日志
 * @date 2022-12-03
 */
public interface AccessLogService {
    /**
     * 发送访问日志
     * @param req 请求
     */
    void sendAccessLog(HttpServletRequest req);

}
