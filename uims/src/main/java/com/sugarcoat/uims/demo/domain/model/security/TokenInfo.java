package com.sugarcoat.uims.demo.domain.model.security;

import lombok.Data;

/**
 * @author xxd
 * @description 安全信息
 * @date 2022-10-28
 */
@Data
public class TokenInfo {
    private String uuid;
    private Long loginTime;
    private Long lastActiveTime;
    private Long lastRefreshTime;
    private Long maxActiveTime;
    private String os;
    private String ip;
    private String mac;
    private String browser;
}