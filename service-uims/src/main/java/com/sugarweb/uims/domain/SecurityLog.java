package com.sugarweb.uims.domain;

import lombok.*;

/**
 * 安全日志
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SecurityLog {

    /**
     * 事件类型
     */
    private String id;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 操作ip
     */
    private String actionIp;

    /**
     * 操作类型
     */
    private String actionType;

    /**
     * 相关信息
     */
    private String message;

}
