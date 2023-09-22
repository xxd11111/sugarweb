package com.sugarcoat.protocol.exception;

import com.sugarcoat.protocol.common.HttpCode;

public class IdempotentException extends BaseException {

    public IdempotentException() {
        super();
    }

    public IdempotentException(String message, Object... objects) {
        super(message, objects);
    }

    public IdempotentException(HttpCode httpCode) {
        super(httpCode);
    }

    public IdempotentException(Integer code, String message) {
        super(code, message);
    }

}
