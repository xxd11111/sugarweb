package com.sugarcoat.protocol.exception;

import com.sugarcoat.protocol.common.HttpCode;

public class RateLimitException extends BaseException {

    public RateLimitException() {
    }

    public RateLimitException(String message, Object... objects) {
        super(message, objects);
    }

    public RateLimitException(HttpCode httpCode) {
        super(httpCode);
    }

    public RateLimitException(Integer code, String message) {
        super(code, message);
    }
}