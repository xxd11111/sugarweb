package com.sugarcoat.protocol.server;

import java.time.LocalDateTime;

/**
 * api调用日志
 *
 * @author 许向东
 * @date 2023/9/22
 */
public interface ApiCallLog {

    String getApiId();

    String getAppIp();

    String getAppPort();

    String getRequestUrl();

    String getRequestParam();

    String getRequestMethod();

    int getResultCode();

    String getResultMsg();

    LocalDateTime getCallDate();

    LocalDateTime getCallEndDate();

    int getDuration();

    String getUserId();

    String getUserName();

    String getUserIp();

    String getUserAgent();

}
