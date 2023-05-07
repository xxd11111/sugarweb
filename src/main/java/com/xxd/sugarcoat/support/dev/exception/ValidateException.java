package com.xxd.sugarcoat.support.dev.exception;

import cn.hutool.core.util.StrUtil;
import com.xxd.sugarcoat.support.prod.common.HttpCode;

/**
 * @author xxd
 * @description 校验异常（参数规范问题）
 * @date 2022-11-12
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
        this.message = StrUtil.format(message, objects);
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
