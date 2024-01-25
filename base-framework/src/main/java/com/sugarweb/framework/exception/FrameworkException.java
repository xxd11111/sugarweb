package com.sugarweb.framework.exception;

import com.sugarweb.framework.common.HttpCode;

/**
 * 用于框架组件抛出异常（框架初始化问题，框架使用异常）
 *
 * @author xxd
 * @version 1.0
 */
public class FrameworkException extends BaseException {

    public FrameworkException() {
    }

    public FrameworkException(String message, Throwable e) {
        super(message, e);
    }

    public FrameworkException(String message, Object... objects) {
        super(message, objects);
    }

    public FrameworkException(HttpCode httpCode) {
        super(httpCode);
    }

    public FrameworkException(Integer code, String message) {
        super(code, message);
    }
}
