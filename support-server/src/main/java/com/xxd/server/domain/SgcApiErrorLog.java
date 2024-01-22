package com.xxd.server.domain;

import lombok.*;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

/**
 * 接口异常日志
 *
 * @author xxd
 * @since 2022-10-27
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class SgcApiErrorLog extends SgcApiCallLog {

    /**
     * 异常时间
     */
    private LocalDateTime exceptionTime;
    /**
     * 异常名
     */
    private String exceptionName;
    /**
     * 异常发生的类全名
     */
    private String exceptionClassName;
    /**
     * 异常发生的类文件
     */
    private String exceptionFileName;
    /**
     * 异常发生的方法名
     */
    private String exceptionMethodName;
    /**
     * 异常发生的方法所在行
     */
    private Integer exceptionLineNumber;
    /**
     * 异常的栈轨迹异常的栈轨迹
     */
    private String exceptionStackTrace;
    /**
     * 异常导致的根消息
     */
    private String exceptionRootCauseMessage;
    /**
     * 异常导致的消息
     */
    private String exceptionMessage;

}
