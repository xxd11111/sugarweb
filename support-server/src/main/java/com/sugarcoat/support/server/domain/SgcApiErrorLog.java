package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.server.ApiErrorLog;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 接口异常日志
 *
 * @author xxd
 * @since 2022-10-27
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class SgcApiErrorLog implements ApiErrorLog {

	@Id
	private String id;

	private String apiId;

	private String appIp;

	private String appPort;

	private LocalDateTime callDate;

	/**
	 * 账号编号
	 */
	private Long userId;

	/**
	 * 用户类型
	 */
	private Integer userType;

	/**
	 * 应用名
	 */
	@NotNull(message = "应用名不能为空")
	private String applicationName;

	/**
	 * 请求方法名
	 */
	@NotNull(message = "http 请求方法不能为空")
	private String requestMethod;

	/**
	 * 访问地址
	 */
	@NotNull(message = "访问地址不能为空")
	private String requestUrl;

	/**
	 * 请求参数
	 */
	@NotNull(message = "请求参数不能为空")
	private String requestParam;

	/**
	 * 用户 IP
	 */
	@NotNull(message = "ip 不能为空")
	private String userIp;

	/**
	 * 浏览器 UA
	 */
	@NotNull(message = "User-Agent 不能为空")
	private String userAgent;

	/**
	 * 异常名
	 */
	@NotNull(message = "异常名不能为空")
	private String errorName;

	/**
	 * 异常发生的类全名
	 */
	@NotNull(message = "异常发生的类全名不能为空")
	private String errorClassName;

	/**
	 * 异常发生的类文件
	 */
	@NotNull(message = "异常发生的类文件不能为空")
	private String errorFileName;

	/**
	 * 异常发生的方法名
	 */
	@NotNull(message = "异常发生的方法名不能为空")
	private String errorMethodName;

	/**
	 * 异常发生的方法所在行
	 */
	@NotNull(message = "异常发生的方法所在行不能为空")
	private Integer errorLineNumber;

	/**
	 * 异常的栈轨迹异常的栈轨迹
	 */
	@NotNull(message = "异常的栈轨迹不能为空")
	private String errorStackTrace;

	/**
	 * 异常导致的根消息
	 */
	@NotNull(message = "异常导致的根消息不能为空")
	private String errorRootCauseMessage;

	/**
	 * 异常导致的消息
	 */
	@NotNull(message = "异常导致的消息不能为空")
	private String errorMessage;

}
