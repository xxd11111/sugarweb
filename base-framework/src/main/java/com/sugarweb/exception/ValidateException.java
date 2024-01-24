package com.sugarweb.exception;

import com.sugarweb.common.HttpCode;

/**
 * 校验异常（接口层输入不符合规范，过滤，与业务无关）
 *
 * @author xxd
 * @version 1.0
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
