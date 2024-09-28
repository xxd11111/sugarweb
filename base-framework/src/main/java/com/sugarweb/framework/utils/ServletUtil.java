package com.sugarweb.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.exception.FrameworkException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ServletUtil
 *
 * @author xxd
 * @version 1.0
 */
public class ServletUtil {

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            throw new FrameworkException("not a ServletRequestAttributes");
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getRequestIp() {
        return getRequest().getRemoteAddr();
    }

    public static String getUserAgent() {
        return StrUtil.nullToEmpty(getRequest().getHeader("User-Agent"));
    }

}
