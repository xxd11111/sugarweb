package com.sugarcoat.support.orm.datapermission;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DsStrUtils;
import com.sugarcoat.protocol.exception.FrameworkException;

import java.util.*;

/**
 * TODO
 *
 * @author xxd
 * @since 2023/11/15 20:41
 */
public class DataPermissionContext {

    public static final String all = "0";
    public static final String currentAndSubOrg = "1";
    public static final String currentOrg = "2";

    private static final ThreadLocal<Collection<String>> allowOrgIds = new ThreadLocal<>();

    private static final ThreadLocal<String> currentOrgId = new ThreadLocal<>();

    private static final ThreadLocal<String> userId = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, String>> customStrategy = ThreadLocal.withInitial(HashMap::new);

    private static final ThreadLocal<String> globalStrategy = new ThreadLocal<>();

    private static final ThreadLocal<Boolean> isRoot = ThreadLocal.withInitial(() -> false);

    private static final ThreadLocal<Deque<String>> sessionCustomKey = ThreadLocal.withInitial(ArrayDeque::new);

    public static String peekSessionCustomKey() {
        return sessionCustomKey.get().peek();
    }

    public static String pushSessionCustomKey(String ds) {
        String dataSourceStr = DsStrUtils.isEmpty(ds) ? "" : ds;
        sessionCustomKey.get().push(dataSourceStr);
        return dataSourceStr;
    }

    public static void pollSessionCustomKey() {
        Deque<String> deque = sessionCustomKey.get();
        deque.poll();
        if (deque.isEmpty()) {
            sessionCustomKey.remove();
        }
    }

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
        DataPermissionContext.sessionCustomKey.remove();
    }

    public static String getStrategy(String customKey) {
        String strategy;
        if (StrUtil.isNotEmpty(customKey)) {
            strategy = DataPermissionContext.getCustomStrategy().get(customKey);
        } else {
            strategy = DataPermissionContext.getGlobalStrategy();
        }
        if (StrUtil.isEmpty(strategy)) {
            strategy = currentOrg;
        }
        return strategy;
    }

    public static Object getValue(String strategy) {
        if (StrUtil.equals(currentOrg, strategy)) {
            return DataPermissionContext.getCurrentOrgId();
        } else if (StrUtil.equals(currentAndSubOrg, strategy)) {
            return DataPermissionContext.getAllowOrgIds();
        } else {
            return currentAndSubOrg;
        }
    }
}
