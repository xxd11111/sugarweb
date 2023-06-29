package com.sugarcoat.uims.application.dto;

import com.sugarcoat.api.common.BooleanFlag;
import com.sugarcoat.uims.domain.menu.MenuType;
import com.sugarcoat.uims.domain.menu.MenuUrlType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Set;

/**
 * 菜单树vo
 *
 * @author xxd
 * @date 2022-12-29
 */
@Data
public class MenuTreeVo {
    /**
     * 菜单id
     */
    private String id;

    /**
     * 菜单pid
     */
    private String pid;

    /**
     * 菜单pid
     */
    private Set<MenuTreeVo> menuTrees;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单类型
     */
    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    /**
     * url类型
     */
    @Enumerated(EnumType.STRING)
    private MenuUrlType menuUrlType;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单顺序
     */
    private Integer sequence;

    /**
     * 菜单权限
     */
    private Set<String> serverApis;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private BooleanFlag enable;
}
