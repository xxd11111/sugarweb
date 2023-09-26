package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.security.SecurityHelper;
import com.sugarcoat.protocol.security.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * api日志信息处理 解决跨方法传递参数问题
 *
 * @author 许向东
 * @date 2023/9/22
 */
public class ApiLogInfoHandler {

    // private static ThreadLocal<SgcApiCallLog>

    public static void loadRequestInfo(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
    }

    public static void loadResponseInfo() {

    }

    public static void loadUserInfo() {
        UserInfo userInfo = SecurityHelper.currentAccount();
        String id = userInfo.getId();
        String username = userInfo.getUsername();
        String userType = userInfo.getUserType();
    }

    public static void loadErrorInfo() {

    }

    public static void sendLog(){

    }

    public static void sendErrorLog(){

    }

}
