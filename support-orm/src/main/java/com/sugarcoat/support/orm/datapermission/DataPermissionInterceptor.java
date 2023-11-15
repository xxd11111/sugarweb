package com.sugarcoat.support.orm.datapermission;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

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
        // DataPermissionContext.setAllowOrgIds(dataPermissionResolver.getOrgIds());
        // DataPermissionContext.setCurrentOrgId(dataPermissionResolver.getOrgId());
        // DataPermissionContext.setUserId(dataPermissionResolver.getUserId());
        // DataPermissionContext.setRoot(dataPermissionResolver.isRoot());
        // DataPermissionContext.setCustomStrategy(dataPermissionResolver.getCustomStrategy());
        // DataPermissionContext.setGlobalStrategy(dataPermissionResolver.getGlobalStrategy());
        // DataPermissionContext.setAllowOrgIds();
        // DataPermissionContext.setCurrentOrgId();
        // DataPermissionContext.setUserId();
        // DataPermissionContext.setRoot();
        // DataPermissionContext.setCustomStrategy();
        // DataPermissionContext.setGlobalStrategy();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DataPermissionContext.remove();
    }

}
