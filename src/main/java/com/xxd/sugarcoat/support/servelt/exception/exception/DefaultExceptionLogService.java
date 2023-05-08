package com.xxd.sugarcoat.support.servelt.exception.exception;

import com.xxd.sugarcoat.support.servelt.exception.api.ExceptionLogService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxd
 * @description 默认日志客户端
 * @date 2022-11-21
 */
@Component
public class DefaultExceptionLogService implements ExceptionLogService {
    @Override
    public void sendExceptionLog(HttpServletRequest req, Throwable ex) {
        //ignore
    }

}
