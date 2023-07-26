package com.sugarcoat.uims.security;

import lombok.Data;

/**
 * 会话信息
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
@Data
public class SessionInfo {

    private String sessionId;

    private String userId;

    private String ip;

    private String mac;

    private Long loginTime;

    private Long lastRefreshTime;

    private Long maxActiveTime;

    private String platform;

    private String userAgent;

}
