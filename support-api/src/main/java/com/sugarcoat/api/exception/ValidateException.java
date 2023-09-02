package com.sugarcoat.api.exception;

import com.sugarcoat.api.common.HttpCode;

/**
 * 校验异常（接口层输入不符合规范，过滤，与业务无关）
 *
 * @author xxd
 * @since 2022-11-12
 */
public class ValidateException extends RuntimeException {

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
    public ValidateException() {
    }

    public ValidateException(String message, Object... objects) {
        this.message = String.format(message, objects);
    }

    public ValidateException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public ValidateException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public ValidateException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ValidateException setMessage(String message) {
        this.message = message;
        return this;
    }

}
