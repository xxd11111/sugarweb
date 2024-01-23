package com.xxd.exception;

import com.xxd.common.HttpCode;

/**
 * 校验异常（接口层输入不符合规范，过滤，与业务无关）
 *
 * @author xxd
 * @since 2022-11-12
 */
public class ValidateException extends BaseException {

    public ValidateException() {
    }

    public ValidateException(String message, Object... objects) {
        super(message, objects);
    }

    public ValidateException(HttpCode httpCode) {
        super(httpCode);
    }

    public ValidateException(Integer code, String message) {
        super(code, message);
    }
}
