package com.sugarcoat.support.sms;

import lombok.Data;

/**
 * 阿里云sms属性
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/8
 */
@Data
public class AliyunSmsProperties {

	/**
	 * 配置节点
	 */
	private String endpoint = "dysmsapi.aliyuncs.com";

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

}
