package com.sugarcoat.uims.application.dto;

import com.sugarcoat.protocol.common.BooleanFlag;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * 菜单查询条件vo
 *
 * @author xxd
 * @since 2023/6/26 22:23
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
    private BooleanFlag enable;

}
