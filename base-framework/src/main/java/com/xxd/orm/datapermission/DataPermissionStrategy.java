package com.xxd.orm.datapermission;

import com.xxd.common.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DataPermissionStrategy
 *
 * @author 许向东
 * @date 2023/11/17
 */
@Getter
@AllArgsConstructor
public enum DataPermissionStrategy implements EnumValue<String> {

    all("0"),
    currentAndSubOrg("1"),
    currentOrg("2");

    private final String value;

}
