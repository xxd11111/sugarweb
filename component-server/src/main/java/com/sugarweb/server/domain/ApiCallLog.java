package com.sugarweb.server.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 访问日志
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ApiCallLog {

	/**
	 * 日志 id
	 */
	@TableId
	private String logId;

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
	private String methodParams;

	/**
	 * 请求代理
	 */
	private String userAgent;

	/**
	 * 开始请求时间
	 */
	private LocalDateTime startTime;

	/**
	 * 结束请求时间
	 */
	private LocalDateTime endTime;

	/**
	 * 执行时长，单位：毫秒
	 */
	private Integer costTime;

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
	private String resultMessage;

	private String exceptionMessage;

	private String exceptionClassName;

	private String exceptionStackTrace;

}
