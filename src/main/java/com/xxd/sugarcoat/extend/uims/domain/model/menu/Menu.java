package com.xxd.sugarcoat.extend.uims.domain.model.menu;

import com.xxd.sugarcoat.extend.uims.domain.model.api.Api;
import com.xxd.sugarcoat.support.status.AccessibleEnum;
import lombok.Data;

import java.util.Collection;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-07
 */
@Data
public class Menu {
    /**
     * 菜单id
     */
    private Long id;
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
    private MenuTypeEnum menuType;
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
    private Integer order;
    /**
     * 菜单接口
     */
    private Collection<Api> apis;
    /**
     * 状态
     */
    private AccessibleEnum accessible;
}
