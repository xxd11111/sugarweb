package com.sugarcoat.protocol.server;

import java.time.LocalDateTime;

/**
 * api错误日志
 *
 * @author 许向东
 * @date 2023/9/22
 */
public interface ApiErrorLog extends ApiCallLog {

    /**
     * 异常时间
     */
    LocalDateTime getExceptionTime();
    /**
     * 异常名
     */
    String getExceptionName();
    /**
     * 异常发生的类全名
     */
    String getExceptionClassName();
    /**
     * 异常发生的类文件
     */
    String getExceptionFileName();
    /**
     * 异常发生的方法名
     */
    String getExceptionMethodName();
    /**
     * 异常发生的方法所在行
     */
    Integer getExceptionLineNumber();
    /**
     * 异常的栈轨迹异常的栈轨迹
     */
    String getExceptionStackTrace();
    /**
     * 异常导致的根消息
     */
    String getExceptionRootCauseMessage();
    /**
     * 异常导致的消息
     */
    String getExceptionMessage();

}
