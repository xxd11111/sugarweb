package com.xxd.exception;

import com.xxd.common.HttpCode;

/**
 * 业务逻辑异常（不符合业务逻辑，禁止删除，状态判断等业务逻辑问题）
 *
 * @author xxd
 * @version 1.0
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
