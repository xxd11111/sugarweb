package com.sugarweb.uims.domain.dto;

import com.sugarweb.framework.common.Flag;
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
    private Flag enable;

}
