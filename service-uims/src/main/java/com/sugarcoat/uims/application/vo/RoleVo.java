package com.sugarcoat.uims.application.vo;

import com.xxd.orm.BooleanEnum;
import lombok.Data;

import java.util.Collection;

/**
 * 角色详情vo
 *
 * @author xxd
 * @since 2023/6/26 21:58
 */
@Data
public class RoleVo {

    private Long id;

    private String roleName;

    private String roleCode;

    private Collection<MenuTreeVo> menus;

    private BooleanEnum enable;
}
