package com.sugarweb.uims.application.dto;

import com.sugarweb.framework.orm.BooleanEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * 菜单查询条件vo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class MenuQueryDto {

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private BooleanEnum enable;

}
