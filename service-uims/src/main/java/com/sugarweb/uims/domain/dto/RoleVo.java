package com.sugarweb.uims.domain.dto;

import com.sugarweb.framework.common.BooleanFlag;
import lombok.Data;

import java.util.Collection;

/**
 * 角色详情vo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class RoleVo {

    private Long id;

    private String roleName;

    private String roleCode;

    private Collection<MenuTreeVo> menus;

    private BooleanFlag enable;
}
