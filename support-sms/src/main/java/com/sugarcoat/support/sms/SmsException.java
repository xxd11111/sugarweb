package com.sugarcoat.support.sms;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.common.HttpCode;

/**
 * 短信异常
 *
 * @author 许向东
 * @date 2023/9/15
 */
public class SmsException extends RuntimeException {

    /**
     * 全局错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public SmsException() {
    }

    public SmsException(String message, Object... objects) {
        this.message = StrUtil.format(message, objects);
    }

    public SmsException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public SmsException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public SmsException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SmsException setMessage(String message) {
        this.message = message;
        return this;
    }
}
