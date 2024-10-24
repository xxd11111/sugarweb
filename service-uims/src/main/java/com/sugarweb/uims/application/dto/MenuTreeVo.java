package com.sugarweb.uims.application.dto;

import com.sugarweb.uims.constans.MenuType;
import com.sugarweb.uims.constans.MenuUrlType;
import com.sugarweb.framework.common.Flag;
import lombok.Data;

import java.util.Set;

/**
 * 菜单树vo
 *
 * @author xxd
 * @version 1.0
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
    private Flag enable;
}
