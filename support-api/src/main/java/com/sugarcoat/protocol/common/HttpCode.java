package com.sugarcoat.protocol.common;

/**
 * http常用code
 *
 * @author xxd
 * @since 2022-11-13
 */
public enum HttpCode implements ErrorCode {
    OK(200, "请求成功"),
    BAD_REQUEST(400, "请求失败"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "未授权"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "不允许使用该方法"),
    INTERNAL_SERVER_ERROR(500, "系统异常"),
    NOT_IMPLEMENTED(501, "功能未实现");

    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private final int code;

    private final String msg;

}
