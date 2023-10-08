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

    String getApiName();

    String getUserId();

    String getUsername();

    String getRequestIp();

    String getRequestMethod();

    String getRequestUrl();

    String getRequestParams();

    LocalDateTime getRequestTime();

    String getRequestUserAgent();

    Integer getDuration();

    String getResultData();

    Integer getResultCode();

    String getResultMsg();

}
