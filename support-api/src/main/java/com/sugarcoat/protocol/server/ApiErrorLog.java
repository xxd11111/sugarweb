package com.sugarcoat.protocol.server;

/**
 * api错误日志
 *
 * @author 许向东
 * @date 2023/9/22
 */
public interface ApiErrorLog {

    String getApiId();

    String getErrorName();

    String getErrorMessage();

}
