package com.sugarcoat.protocol.exception;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.HttpCode;

/**
 * @author xxd
 * @description 与安全相关异常（异常攻击等问题）
 * @date 2022-11-12
 */
public class SecurityException extends RuntimeException {
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
    public SecurityException() {
    }

    public SecurityException(String message, Object... objects) {
        this.message = StrUtil.format(message, objects);
    }

    public SecurityException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public SecurityException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public SecurityException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SecurityException setMessage(String message) {
        this.message = message;
        return this;
    }
}
