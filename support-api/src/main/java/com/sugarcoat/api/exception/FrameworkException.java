package com.sugarcoat.api.exception;

import com.sugarcoat.api.common.HttpCode;

/**
 * @author xxd
 * @description 用于框架组件抛出异常（框架初始化问题，框架使用异常）
 * @date 2022-11-12
 */
public class FrameworkException extends RuntimeException {

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
	public FrameworkException() {
	}

	public FrameworkException(String message, Object... objects) {
		this.message = String.format(message, objects);
	}

	public FrameworkException(HttpCode httpCode) {
		this.code = httpCode.getCode();
		this.message = httpCode.getMsg();
	}

	public FrameworkException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public FrameworkException setCode(Integer code) {
		this.code = code;
		return this;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public FrameworkException setMessage(String message) {
		this.message = message;
		return this;
	}

}
