package com.xxd.orm.datapermission;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * DataPermissionFilterAspect
 *
 * @author xxd
 * @version 1.0
 */
public class DataPermissionMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DataPermissionContext.setIgnore(true);
        return invocation.proceed();
    }

}
