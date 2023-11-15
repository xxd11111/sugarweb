package com.sugarcoat.support.orm.datapermission;

import java.util.Collection;
import java.util.Map;

/**
 * TODO
 *
 * @author xxd
 * @since 2023/11/15 20:41
 */
public class DataPermissionContext {

    private static final ThreadLocal<Collection<String>> allowOrgIds = new ThreadLocal<>();

    private static final ThreadLocal<String> currentOrgId = new ThreadLocal<>();

    private static final ThreadLocal<String> userId = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, String>> customStrategy = new ThreadLocal<>();

    private static final ThreadLocal<String> globalStrategy = new ThreadLocal<>();

    private static final ThreadLocal<Boolean> isRoot = new ThreadLocal<>();

    public static void setAllowOrgIds(Collection<String> allowOrgIds) {
        DataPermissionContext.allowOrgIds.set(allowOrgIds);
    }

    public static Collection<String> getAllowOrgIds() {
        return DataPermissionContext.allowOrgIds.get();
    }

    public static void setCurrentOrgId(String currentOrgId) {
        DataPermissionContext.currentOrgId.set(currentOrgId);
    }

    public static String getCurrentOrgId() {
        return DataPermissionContext.currentOrgId.get();
    }

    public static void setUserId(String userId) {
        DataPermissionContext.userId.set(userId);
    }

    public static String getUserId() {
        return DataPermissionContext.userId.get();
    }

    public static void setCustomStrategy(Map<String, String> customStrategy) {
        DataPermissionContext.customStrategy.set(customStrategy);
    }

    public static Map<String, String> getCustomStrategy() {
        return DataPermissionContext.customStrategy.get();
    }

    public static void setGlobalStrategy(String globalStrategy) {
        DataPermissionContext.globalStrategy.set(globalStrategy);
    }

    public static String getGlobalStrategy() {
        return DataPermissionContext.globalStrategy.get();
    }

    public static void setRoot(boolean isRoot) {
        DataPermissionContext.isRoot.set(isRoot);
    }

    public static boolean isRoot() {
        return isRoot.get();
    }

    public static void remove() {
        DataPermissionContext.allowOrgIds.remove();
        DataPermissionContext.currentOrgId.remove();
        DataPermissionContext.userId.remove();
        DataPermissionContext.customStrategy.remove();
        DataPermissionContext.globalStrategy.remove();
        DataPermissionContext.isRoot.remove();
    }


}
