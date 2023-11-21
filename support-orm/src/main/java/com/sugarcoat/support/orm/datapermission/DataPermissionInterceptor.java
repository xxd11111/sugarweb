package com.sugarcoat.support.orm.datapermission;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashSet;

/**
 * 数据权限拦截器
 *
 * @author xxd
 * @since 2023/11/15 21:04
 */

public class DataPermissionInterceptor implements HandlerInterceptor {

    // private final DataPermissionResolver dataPermissionResolver;
    //
    // public DataPermissionInterceptor(DataPermissionResolver dataPermissionResolver) {
    //     this.dataPermissionResolver = dataPermissionResolver;
    // }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        DataPermissionInfo dataPermissionInfo = new DataPermissionInfo();
        dataPermissionInfo.setOrgId("1");
        dataPermissionInfo.setRoot(false);
        dataPermissionInfo.setAllowOrgIds(new HashSet<>());
        dataPermissionInfo.setStrategy(DataPermissionStrategy.currentOrg.getValue());
        dataPermissionInfo.setAllowOrgIds(new HashSet<>());
        DataPermissionContext.setDataPermissionInfo(dataPermissionInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DataPermissionContext.remove();
    }

}
