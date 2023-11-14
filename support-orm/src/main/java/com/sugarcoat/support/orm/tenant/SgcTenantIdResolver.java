package com.sugarcoat.support.orm.tenant;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.Map;

/**
 * 租户id解析
 *
 * @author xxd
 * @since 2023/10/21 15:26
 */
public class SgcTenantIdResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {
    @Override
    public boolean isRoot(String tenantId) {
        return TenantContext.isIgnore();
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContext.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
