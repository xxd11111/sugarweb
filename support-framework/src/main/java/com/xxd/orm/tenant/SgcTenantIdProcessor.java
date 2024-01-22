package com.xxd.orm.tenant;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import org.aopalliance.intercept.MethodInvocation;

public class SgcTenantIdProcessor extends DsProcessor {

    /**
     * 等于#tenantId
     */
    private static final String TENANT_ID_KEY = "#tenantId";

    @Override
    public boolean matches(String key) {
        return key.equals(TENANT_ID_KEY);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        return TenantContext.getTenantId();
    }
}