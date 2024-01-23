package com.xxd.orm.tenant;

/**
 * TenantContext
 *
 * @author 许向东
 * @version 1.0
 */
public class TenantContext {

    private static final ThreadLocal<Boolean> tenantIgnore = ThreadLocal.withInitial(() -> false);

    /**
     * 为空时抛异常
     */
    private static final ThreadLocal<String> tenantId = ThreadLocal.withInitial(() -> "");

    public static boolean isIgnore() {
        return tenantIgnore.get();
    }

    public static void setTenantIgnore(boolean ignore) {
        tenantIgnore.set(ignore);
    }

    public static String getTenantId() {
        return tenantId.get();
    }

    public static void setTenantId(String tenantId) {
        TenantContext.tenantId.set(tenantId);
    }

    public static void remove() {
        tenantIgnore.remove();
        tenantId.remove();
    }


}
