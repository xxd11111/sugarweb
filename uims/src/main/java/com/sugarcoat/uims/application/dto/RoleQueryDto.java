package com.sugarcoat.uims.application.dto;

import com.sugarcoat.protocol.common.BooleanEnum;
import lombok.Data;

/**
 * 角色查询dto
 *
 * @author xxd
 * @since 2023/6/26 22:16
 */
@Data
public class RoleQueryDto {

    private String roleName;

    private String roleCode;

    private BooleanEnum enable;

}
