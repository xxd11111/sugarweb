package com.sugarweb.uims.domain.dto;

import com.sugarweb.framework.common.BooleanFlag;
import lombok.Data;

import java.util.Collection;

/**
 * 角色dto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class RoleDto {
    private String id;

    private String roleName;

    private String roleCode;

    private Collection<String> menus;

    private BooleanFlag enable;
}
