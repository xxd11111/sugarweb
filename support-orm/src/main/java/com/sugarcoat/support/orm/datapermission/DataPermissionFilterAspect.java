package com.sugarcoat.support.orm.datapermission;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.BeanUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;


/**
 * DataPermissionFilterAspect
 *
 * @author xxd
 * @since 2023/11/15 22:54
 */
@Aspect
public class DataPermissionFilterAspect {

    private final String all = "0";
    private final String currentAndSubOrg = "1";
    private final String currentOrg = "2";

    @Around("@annotation(dataPermissionFilter)")
    public Object apiLog(ProceedingJoinPoint joinPoint, DataPermissionFilter dataPermissionFilter) throws Throwable {
        String strategy = getStrategy(dataPermissionFilter.customStrategy());
        if (!(DataPermissionContext.isRoot() || StrUtil.equals(all, strategy))) {
            Object value;
            if (StrUtil.equals(currentOrg, strategy)) {
                value = DataPermissionContext.getCurrentOrgId();
            } else if (StrUtil.equals(currentAndSubOrg, strategy)) {
                value = DataPermissionContext.getAllowOrgIds();
            } else {
                throw new FrameworkException("dataPermission不存在此策略：" + strategy);
            }
            EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter(DataPermissionBinder.FILTER_NAME)
                    .setParameter(DataPermissionBinder.PARAMETER_NAME, value);
        }
        //执行方法
        return joinPoint.proceed();
    }

    private String getStrategy(String customStrategy) {
        String strategy;
        if (StrUtil.isNotEmpty(customStrategy)) {
            strategy = DataPermissionContext.getCustomStrategy().get(customStrategy);
        } else {
            strategy = DataPermissionContext.getGlobalStrategy();
        }
        if (StrUtil.isEmpty(strategy)) {
            strategy = currentOrg;
        }
        return strategy;
    }

}
