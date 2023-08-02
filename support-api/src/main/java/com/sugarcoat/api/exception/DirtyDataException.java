package com.sugarcoat.api.exception;

import com.sugarcoat.api.common.HttpCode;

/**
 * 脏数据异常（例如预期能读到的数据，预期只能读一条的数据，需要运维直接处理的数据）
 *
 * @author xxd
 * @date 2023/8/2 22:42
 */
public class DirtyDataException extends RuntimeException {

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
    public DirtyDataException() {
    }

    public DirtyDataException(String message, Object... objects) {
        this.message = String.format(message, objects);
    }

    public DirtyDataException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public DirtyDataException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public DirtyDataException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public DirtyDataException setMessage(String message) {
        this.message = message;
        return this;
    }
}
