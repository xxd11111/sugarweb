package com.sugarweb.framework.exception;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.common.HttpCode;
import lombok.Getter;

/**
 * 基础异常
 *
 * @author xxd
 * @version 1.0
 */
class BaseException extends RuntimeException{

    /**
     * 全局错误码
     */
    @Getter
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

    public BaseException(Throwable e) {
        super(e);
    }

    public BaseException(String message, Throwable e) {
        super(message, e);
    }

    public BaseException(String message, Object... objects) {
        this.message = StrUtil.format(message, objects);
    }

    public BaseException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException setCode(Integer code) {
        this.code = code;
        return this;
    }

    
    public String getMessage() {
        return message;
    }

    public BaseException setMessage(String message) {
        this.message = message;
        return this;
    }

}
