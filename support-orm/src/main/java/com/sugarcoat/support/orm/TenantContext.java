package com.sugarcoat.support.orm;

/**
 * TenantContext
 *
 * @author 许向东
 * @date 2023/10/31
 */
public class TenantContext {

    private static final ThreadLocal<Boolean> tenantIdIgnore = ThreadLocal.withInitial(() -> false);

    public static boolean isIgnore(){
        return tenantIdIgnore.get();
    }

    public static void setTenantId(boolean ignore){
        tenantIdIgnore.set(ignore);
    }

}
