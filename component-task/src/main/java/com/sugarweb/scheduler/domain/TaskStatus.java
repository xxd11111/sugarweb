package com.sugarweb.scheduler.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TaskStatus
 *
 * @author 许向东
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum TaskStatus {

    /**
     * 找不到bean
     */
    BEAN_NOT_FOUND("2"),

    /**
     * 运行中
     */
    RUNNING("1"),

    /**
     * 停止
     */
    STOP("0");

    private final String code;

}
