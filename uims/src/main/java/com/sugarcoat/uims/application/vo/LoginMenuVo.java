package com.sugarcoat.uims.application.vo;

import com.sugarcoat.uims.domain.menu.MenuType;
import com.sugarcoat.uims.domain.menu.MenuUrlType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Set;

/**
 * 登录菜单vo
 *
 * @author xxd
 * @date 2023/7/2 10:19
 */
@Data
public class LoginMenuVo {

    /**
     * 菜单pid
     */
    private Set<LoginMenuVo> menus;

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

}
