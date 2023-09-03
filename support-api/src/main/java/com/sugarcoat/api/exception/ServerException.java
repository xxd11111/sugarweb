package com.sugarcoat.api.exception;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.common.HttpCode;

/**
 * 服务器异常（定时任务，中间件异常，磁盘问题）若细分可补充
 *
 * @author xxd
 * @since 2022-11-12
 */
public class ServerException extends RuntimeException {

	/**
	 * 全局错误码
	 */
	private Integer code;

	/**
	 * 错误提示
	 */
	private String message;

	/**
	 * 空构造方法，避免反序列化问题
	 */
	public ServerException() {
	}

	public ServerException(String message, Object... objects) {
		this.message = StrUtil.format(message, objects);
	}

	public ServerException(HttpCode httpCode) {
		this.code = httpCode.getCode();
		this.message = httpCode.getMsg();
	}

	public ServerException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public ServerException setCode(Integer code) {
		this.code = code;
		return this;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ServerException setMessage(String message) {
		this.message = message;
		return this;
	}

}
