package com.sugarcoat.support.orm.datapermission;

import java.util.Collection;
import java.util.Map;

/**
 * 数据权限接口类
 *
 * @author xxd
 * @since 2023/11/15 20:56
 */
public interface DataPermissionResolver {

    String getUserId();

    String getOrgId();

    Collection<String> getOrgIds();

    boolean isRoot();

    Map<String, String> getCustomStrategy();

    String getGlobalStrategy();

}
