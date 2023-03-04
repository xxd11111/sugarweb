package com.xxd.sugarcoat.extend.uims.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxd.sugarcoat.extend.uims.Common;
import com.xxd.sugarcoat.support.status.AccessibleEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-12
 */
@Getter
@Setter
@TableName(Common.TABLE_PREFIX + "menu")
public class MenuPO {
    /**
     * 菜单id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 菜单搜索索引
     */
    private String menuIndex;
    /**
     * 菜单名
     */
    private String menuName;
    /**
     * 菜单编码
     */
    private String menuCode;
    /**
     * 菜单类型
     */
    private String menuType;
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
    private Integer orderNum;
    /**
     * 状态
     */
    private AccessibleEnum status;
}
