package com.sugarcoat.support.orm.datapermission;

/**
 * TODO
 *
 * @author xxd
 * @since 2023/11/15 20:41
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
