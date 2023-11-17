package com.sugarcoat.support.orm.datapermission;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * DataPermissionFilterAspect
 *
 * @author xxd
 * @since 2023/11/15 22:54
 */
public class DataPermissionMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DataPermissionContext.setIgnore(true);
        return invocation.proceed();
    }

}
