package com.sugarweb.uims.domain.dto;

import com.sugarweb.uims.domain.constans.MenuType;
import com.sugarweb.uims.domain.constans.MenuUrlType;
import lombok.Data;

import java.util.Set;

/**
 * 登录菜单vo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserMenuVo {

    /**
     * 菜单pid
     */
    private Set<UserMenuVo> menus;

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

}
