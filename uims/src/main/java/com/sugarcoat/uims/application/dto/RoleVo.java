package com.sugarcoat.uims.application.dto;

import com.sugarcoat.api.common.BooleanFlag;
import lombok.Data;

import java.util.Collection;

/**
 * 角色详情vo
 *
 * @author xxd
 * @date 2023/6/26 21:58
 */
@Data
public class RoleVo {

    private Long id;

    private String roleName;

    private String roleCode;

    private Collection<MenuTreeVo> menus;

    private BooleanFlag enable;
}
