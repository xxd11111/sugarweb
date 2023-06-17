package com.sugarcoat.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxd
 * @description 响应对象
 * @date 2022-10-27
 */
@Data
@NoArgsConstructor
public class Result<T> {

	private Integer code;

	private String msg;

	private T data;

	public static <T> Result<T> ok() {
		return build(HttpCode.OK);
	}

	public static <T> Result<T> ok(String msg) {
		return build(null, HttpCode.OK.getCode(), msg);
	}

	public static <T> Result<T> ok(String msg, T data) {
		return build(data, HttpCode.OK.getCode(), msg);
	}

	public static <T> Result<T> data(T data) {
		return build(data, HttpCode.OK);
	}

	public static <T> Result<T> data(T data, String msg) {
		return build(data, HttpCode.OK.getCode(), msg);
	}

	public static <T> Result<T> error() {
		return build(HttpCode.INTERNAL_SERVER_ERROR);
	}

	public static <T> Result<T> error(String msg) {
		return build(null, HttpCode.INTERNAL_SERVER_ERROR.getCode(), msg);
	}

	public static <T> Result<T> error(ErrorCode errorCode) {
		return build(errorCode);
	}

	private static <T> Result<T> build(T data, Integer code, String msg) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}

	private static <T> Result<T> build(T data, ErrorCode errorCode) {
		return build(data, errorCode.getCode(), errorCode.getMsg());
	}

	private static <T> Result<T> build(ErrorCode errorCode) {
		return build(null, errorCode);
	}

}
