package com.sugarweb.orm.datapermission;

/**
 * 数据权限上下文
 *
 * @author xxd
 * @version 1.0
 */
public class DataPermissionContext {

    private static final ThreadLocal<DataPermissionInfo> dataPermissionInfo = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> isIgnore = ThreadLocal.withInitial(() -> false);

    public static void setDataPermissionInfo(DataPermissionInfo dataPermissionInfo) {
        DataPermissionContext.dataPermissionInfo.set(dataPermissionInfo);
    }

    public static DataPermissionInfo getDataPermissionInfo() {
        return DataPermissionContext.dataPermissionInfo.get();
    }

    public static void setIgnore(boolean ignore) {
        DataPermissionContext.isIgnore.set(ignore);
    }

    public static boolean isIgnore() {
        return isIgnore.get();
    }


    public static void remove() {
        DataPermissionContext.dataPermissionInfo.remove();
        DataPermissionContext.isIgnore.remove();
    }

}
