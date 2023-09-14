package com.sugarcoat.support.sms;

import lombok.Data;

/**
 * 腾讯sms属性
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/8
 */
@Data
public class TencentSmsProperties {

	/**
	 * 配置节点
	 */
	private String endpoint = "sms.tencentcloudapi.com";

	/**
	 * key
	 */
	private String accessKeyId;

	/**
	 * 密匙
	 */
	private String accessKeySecret;

	/**
	 * 短信签名
	 */
	private String signName;

	/**
	 * 短信应用ID
	 */
	private String sdkAppId;

}
