package com.sugarweb.uims.application.dto;

import com.sugarweb.framework.common.Flag;
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

    private Flag enable;
}
