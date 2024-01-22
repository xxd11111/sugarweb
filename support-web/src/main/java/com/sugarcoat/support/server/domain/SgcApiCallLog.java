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

	/**
	 * 接口id
	 */
	private String apiId;

	/**
	 * 接口名
	 */
	private String apiName;

	/**
	 * 用户 id
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 请求ip
	 */
	private String requestIp;

	/**
	 * 请求url
	 */
	private String requestUrl;

	/**
	 * 请求方法
	 */
	private String requestMethod;

	/**
	 * 请求参数
	 */
	private String requestParams;

	/**
	 * 请求代理
	 */
	private String requestUserAgent;

	/**
	 * 开始请求时间
	 */
	private LocalDateTime requestTime;

	/**
	 * 执行时长，单位：毫秒
	 */
	private Integer duration;

	/**
	 * 结果码
	 */
	private Integer resultCode;

	/**
	 * 结果数据
	 */
	private String resultData;

	/**
	 * 结果提示
	 */
	private String resultMsg;

}
