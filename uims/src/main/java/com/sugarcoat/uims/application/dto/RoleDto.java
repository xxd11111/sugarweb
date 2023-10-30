package com.sugarcoat.uims.application.dto;

import com.sugarcoat.protocol.common.BooleanEnum;
import lombok.Data;

import java.util.Collection;

/**
 * 角色dto
 *
 * @author xxd
 * @since 2022-12-29
 */
@Data
public class RoleDto {
    private String id;

    private String roleName;

    private String roleCode;

    private Collection<String> menus;

    private BooleanEnum enable;
}
