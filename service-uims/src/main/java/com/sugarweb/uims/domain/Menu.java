package com.sugarweb.uims.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 菜单
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class Menu {

    /**
     * 菜单id
     */
    @TableId
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
    private String menuType;

    /**
     * url类型
     */
    private String menuUrlType;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单顺序
     */
    private Integer menuOrder;

    /**
     * 菜单权限
     */
    private String apiPermission;

    /**
     * 状态
     */
    private String enabled;

}
