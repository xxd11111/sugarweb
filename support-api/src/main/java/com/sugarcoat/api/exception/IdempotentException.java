package com.sugarcoat.api.exception;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.common.HttpCode;

public class IdempotentException extends RuntimeException {

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
    public IdempotentException() {
    }

    public IdempotentException(String message, Object... objects) {
        this.message = StrUtil.format(message, objects);
    }

    public IdempotentException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public IdempotentException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public IdempotentException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public IdempotentException setMessage(String message) {
        this.message = message;
        return this;
    }

}
