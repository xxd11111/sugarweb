package com.xxd.sugarcoat.support.status;

/**
 * @author xxd
 * @description TODO
 * @date 2023-01-10
 */
public class AccessUtil {

    public static boolean isAccess(AccessibleEnum accessibleEnum) {
        return AccessibleEnum.ENABLE.equals(accessibleEnum);
    }
}
