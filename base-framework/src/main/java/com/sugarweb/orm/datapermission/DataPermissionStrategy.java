package com.sugarweb.orm.datapermission;

import com.sugarweb.common.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DataPermissionStrategy
 *
 * @author 许向东
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum DataPermissionStrategy implements EnumValue<String> {

    all("0"),
    currentAndSubOrg("1"),
    currentOrg("2");

    private final String value;

}
