package com.sugarcoat.api.exception;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.common.HttpCode;

/**
 * 业务逻辑异常（不符合业务逻辑，禁止删除等业务逻辑问题）
 * @author xxd
 * @description
 * @since 2022-11-12
 */
public class ServiceException extends RuntimeException {

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
	public ServiceException() {
	}

	public ServiceException(String message, Object... objects) {
		this.message = StrUtil.format(message, objects);
	}

	public ServiceException(HttpCode httpCode) {
		this.code = httpCode.getCode();
		this.message = httpCode.getMsg();
	}

	public ServiceException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public ServiceException setCode(Integer code) {
		this.code = code;
		return this;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ServiceException setMessage(String message) {
		this.message = message;
		return this;
	}

}
