package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.server.ApiCallLog;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * 访问日志
 *
 * @author xxd
 * @since 2022-10-27
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class SgcApiCallLog implements ApiCallLog {

	/**
	 * 日志 id
	 */
	@Id
	private String id;

	private String apiId;

	private String appIp;

	private String appPort;

	/**
	 * 服务ip
	 */
	private String serverIp;

	/**
	 * 请求url
	 */
	private String requestUrl;

	/**
	 * 请求参数
	 */
	private String requestParam;

	/**
	 * 请求方法
	 */
	private String requestMethod;

	/**
	 * 结果码
	 */
	private int resultCode;

	/**
	 * 结果提示
	 */
	private String resultMsg;

	/**
	 * 用户 id
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户 IP
	 */
	private String userIp;

	/**
	 * 浏览器 UA
	 */
	private String userAgent;

	/**
	 * 开始请求时间
	 */
	private LocalDateTime callDate;

	/**
	 * 结束请求时间
	 */
	private LocalDateTime callEndDate;

	/**
	 * 执行时长，单位：毫秒
	 */
	private int duration;

}
