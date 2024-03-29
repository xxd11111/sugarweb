package com.sugarweb.framework.exception;

import com.sugarweb.framework.common.HttpCode;

/**
 * 服务器异常（定时任务，中间件异常，磁盘问题）
 *
 * @author xxd
 * @version 1.0
 */
public class ServerException extends BaseException {

    public ServerException() {
    }

    public ServerException(Throwable e) {
        super(e);
    }

    public ServerException(String message, Object... objects) {
        super(message, objects);
    }

    public ServerException(HttpCode httpCode) {
        super(httpCode);
    }

    public ServerException(Integer code, String message) {
        super(code, message);
    }
}
