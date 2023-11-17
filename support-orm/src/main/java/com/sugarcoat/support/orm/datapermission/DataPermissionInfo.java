package com.sugarcoat.support.orm.datapermission;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/17
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
        return StrUtil.isEmpty(str) ? strategy : str;
    }

    public Object getValue(String strategy) {
        if (StrUtil.equals(strategy, DataPermissionStrategy.currentAndSubOrg.getValue())) {
            return allowOrgIds;
        } else if (StrUtil.equals(strategy, DataPermissionStrategy.currentOrg.getValue())) {
            return orgId;
        } else {
            return orgId;
        }
    }


}
