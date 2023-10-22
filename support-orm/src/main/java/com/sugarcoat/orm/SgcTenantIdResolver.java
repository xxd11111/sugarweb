package com.sugarcoat.orm;

import com.sugarcoat.protocol.security.SecurityHelper;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * 租户id解析
 *
 * @author xxd
 * @since 2023/10/21 15:26
 */
public class SgcTenantIdResolver implements CurrentTenantIdentifierResolver {
    @Override
    public boolean isRoot(String tenantId) {
        String userType = SecurityHelper.getUserType();
        return "supperAdmin".equals(userType);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return SecurityHelper.getUserData("tenantId");
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return !SecurityHelper.isSystemCall();
    }
}
