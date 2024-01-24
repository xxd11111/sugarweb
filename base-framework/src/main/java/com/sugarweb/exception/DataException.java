package com.sugarweb.exception;

import com.sugarweb.common.HttpCode;

/**
 * 数据异常（例如预期能读到的数据，预期只能读一条的数据，需要运维直接处理的数据）
 *
 * @author xxd
 * @version 1.0
 */
public class DataException extends BaseException {
    public DataException() {
    }

    public DataException(String message, Object... objects) {
        super(message, objects);
    }

    public DataException(HttpCode httpCode) {
        super(httpCode);
    }

    public DataException(Integer code, String message) {
        super(code, message);
    }
}
