package com.sugarcoat.uims.application.dto;

import com.sugarcoat.protocol.common.BooleanFlag;
import com.sugarcoat.uims.domain.menu.MenuType;
import com.sugarcoat.uims.domain.menu.MenuUrlType;
import lombok.Data;

import java.util.Set;

/**
 * 菜单dto
 *
 * @author xxd
 * @since 2022-12-29
 */
@Data
public class MenuDto {
    /**
     * 菜单id
     */
    private String id;

    /**
     * 菜单pid
     */
    private String pid;

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
    private MenuType menuType;

    /**
     * url类型
     */
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
    private BooleanFlag enable;
}
