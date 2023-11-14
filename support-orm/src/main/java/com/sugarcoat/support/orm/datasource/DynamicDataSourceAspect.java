package com.sugarcoat.support.orm.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 多数据源切面处理 @DynamicDS
 *
 * @author 许向东
 * @date 2023/11/14
 */
@Aspect
public class DynamicDataSourceAspect {

    @Around("@annotation(dynamicDS)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, DynamicDS dynamicDS) throws Throwable {
        String value = dynamicDS.value();
        DataSourceContext.setDsId(value);
        return proceedingJoinPoint.proceed();
    }
}
