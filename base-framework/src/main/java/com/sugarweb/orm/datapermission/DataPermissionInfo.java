package com.sugarweb.orm.datapermission;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * 数据权限信息
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class DataPermissionInfo {
    private Collection<String> allowOrgIds;
    private String orgId;
    private String userId;
    private String strategy;
    private boolean isRoot;
    private Map<String, String> strategyMap;

    public String getStrategy(String key) {
        String str = strategyMap.get(key);
        return Strings.isNullOrEmpty(str) ? strategy : str;
    }

    public Object getValue(String strategy) {
        if (Objects.equal(strategy, DataPermissionStrategy.currentAndSubOrg.getValue())) {
            return allowOrgIds;
        } else if (Objects.equal(strategy, DataPermissionStrategy.currentOrg.getValue())) {
            return orgId;
        } else {
            return orgId;
        }
    }


}
