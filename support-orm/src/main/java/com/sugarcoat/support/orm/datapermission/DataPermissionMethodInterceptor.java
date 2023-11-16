package com.sugarcoat.support.orm.datapermission;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
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
        String value = "";
        DataPermissionFilter methodAnnotation = invocation.getMethod().getAnnotation(DataPermissionFilter.class);
        if (methodAnnotation != null) {
            value = methodAnnotation.customStrategy();
        } else {
            DataPermissionFilter classAnnotation = invocation.getMethod().getDeclaringClass().getAnnotation(DataPermissionFilter.class);
            if (classAnnotation != null) {
                value = classAnnotation.customStrategy();
            }
        }
        DynamicDataSourceContextHolder.push(value);
        try {
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

}
