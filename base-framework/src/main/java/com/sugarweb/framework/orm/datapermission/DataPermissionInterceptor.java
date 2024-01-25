package com.sugarweb.framework.orm.datapermission;

import com.sugarweb.framework.orm.datapermission.DataPermissionContext;
import com.sugarweb.framework.orm.datapermission.DataPermissionInfo;
import com.sugarweb.framework.orm.datapermission.DataPermissionStrategy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashSet;

/**
 * 数据权限拦截器
 *
 * @author xxd
 * @version 1.0
 */

public class DataPermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //todo 加载数据权限信息
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
