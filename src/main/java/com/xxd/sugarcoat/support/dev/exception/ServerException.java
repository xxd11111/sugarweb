package com.xxd.sugarcoat.support.dev.exception;

import cn.hutool.core.util.StrUtil;
import com.xxd.sugarcoat.support.prod.common.HttpCode;

/**
 * @author xxd
 * @description 服务器异常,用于非api访问时抛出的错误
 * @date 2022-11-12
 */
public class ServerException extends RuntimeException {
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
    public ServerException() {
    }

    public ServerException(String message, Object... objects) {
        this.message = StrUtil.format(message, objects);
    }

    public ServerException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public ServerException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public ServerException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServerException setMessage(String message) {
        this.message = message;
        return this;
    }
}
