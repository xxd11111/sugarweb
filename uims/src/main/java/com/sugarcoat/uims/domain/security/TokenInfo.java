package com.sugarcoat.uims.domain.security;

import lombok.Data;

/**
 * @author xxd
 * @description 安全信息
 * @date 2022-10-28
 */
@Data
public class TokenInfo {

	private String uuid;

	private String ip;

	private Long loginTime;

	private Long lastActiveTime;

	private Long lastRefreshTime;

	private Long maxActiveTime;

	private String platform;

	private String userAgent;

}
