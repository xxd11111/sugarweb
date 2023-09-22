package com.sugarcoat.protocol.exception;

import com.sugarcoat.protocol.common.HttpCode;

/**
 * 与安全相关异常（安全攻击等问题）
 *
 * @author xxd
 * @since 2022-11-12
 */
public class SecurityException extends BaseException {

    public SecurityException() {
    }

    public SecurityException(String message, Object... objects) {
        super(message, objects);
    }

    public SecurityException(HttpCode httpCode) {
        super(httpCode);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }
}
