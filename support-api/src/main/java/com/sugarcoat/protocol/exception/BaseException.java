package com.sugarcoat.protocol.exception;

import com.google.common.base.Strings;
import com.sugarcoat.protocol.common.HttpCode;

/**
 * 基础异常
 *
 * @author xxd
 * @since 2023/9/20 22:54
 */
public class BaseException extends RuntimeException{

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
    public BaseException() {
    }

    public BaseException(String message, Throwable e) {
        super(message, e);
    }

    public BaseException(String message, Object... objects) {
        this.message = Strings.lenientFormat(message, objects);
    }

    public BaseException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public BaseException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public BaseException setMessage(String message) {
        this.message = message;
        return this;
    }

}
