package com.sugarcoat.uims.application.dto;

import com.xxd.orm.BooleanEnum;
import lombok.Data;

/**
 * 角色查询dto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class RoleQueryDto {

    private String roleName;

    private String roleCode;

    private BooleanEnum enable;

}
