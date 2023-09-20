package com.sugarcoat.api.server.exception;

import com.sugarcoat.api.common.HttpCode;

/**
 * 脏数据异常（例如预期能读到的数据，预期只能读一条的数据，需要运维直接处理的数据）
 *
 * @author xxd
 * @since 2023/8/2 22:42
 */
public class DirtyDataException extends BaseException {
    public DirtyDataException() {
    }

    public DirtyDataException(String message, Object... objects) {
        super(message, objects);
    }

    public DirtyDataException(HttpCode httpCode) {
        super(httpCode);
    }

    public DirtyDataException(Integer code, String message) {
        super(code, message);
    }
}
