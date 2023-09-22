package com.sugarcoat.protocol.exception;

import com.sugarcoat.protocol.common.HttpCode;

/**
 * 业务逻辑异常（不符合业务逻辑，禁止删除等业务逻辑问题）
 *
 * @author xxd
 * @description
 * @since 2022-11-12
 */
public class ServiceException extends BaseException {

    public ServiceException() {
    }

    public ServiceException(String message, Object... objects) {
        super(message, objects);
    }

    public ServiceException(HttpCode httpCode) {
        super(httpCode);
    }

    public ServiceException(Integer code, String message) {
        super(code, message);
    }
}
