package com.xxd.server;

/**
 * 接口信息
 *
 * @author 许向东
 * @version 1.0
 */
public interface ApiInfo {

    String getOperationId();

    String getUrl();

    String getRequestMethod();

    String getSummary();

    String getOperationDescription();

    String getTagName();

    String getTagDescription();

}
