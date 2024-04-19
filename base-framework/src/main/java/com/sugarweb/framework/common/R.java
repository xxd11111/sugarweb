package com.sugarweb.framework.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 响应对象
 *
 * @author xxd
 * @version 1.0
 */
@Setter
@Getter
public class R<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return build(HttpCode.OK);
    }

    public static <T> R<T> ok(String msg) {
        return build(null, HttpCode.OK.getCode(), msg);
    }

    public static <T> R<T> ok(String msg, T data) {
        return build(data, HttpCode.OK.getCode(), msg);
    }

    public static <T> R<T> data(T data) {
        return build(data, HttpCode.OK);
    }

    public static <T> R<T> data(T data, String msg) {
        return build(data, HttpCode.OK.getCode(), msg);
    }

    public static <T> R<T> error() {
        return build(HttpCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> R<T> error(String msg) {
        return build(null, HttpCode.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> R<T> error(ErrorCode errorCode) {
        return build(errorCode);
    }

    private static <T> R<T> build(T data, Integer code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    private static <T> R<T> build(T data, ErrorCode errorCode) {
        return build(data, errorCode.getCode(), errorCode.getMsg());
    }

    private static <T> R<T> build(ErrorCode errorCode) {
        return build(null, errorCode);
    }

}
