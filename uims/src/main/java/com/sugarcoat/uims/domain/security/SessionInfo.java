package com.sugarcoat.uims.domain.security;

import com.sugarcoat.api.user.UserInfo;

/**
 * 会话信息
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
public class SessionInfo {

    private String uuid;

    private String ip;

    private Long loginTime;

    private Long lastActiveTime;

    private Long lastRefreshTime;

    private Long maxActiveTime;

    private String platform;

    private String userAgent;

    private UserInfo userInfo;
}
