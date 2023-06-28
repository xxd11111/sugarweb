package com.sugarcoat.uims.application.dto;

import com.sugarcoat.api.common.BooleanFlag;
import lombok.Data;

/**
 * 角色查询dto
 *
 * @author xxd
 * @date 2023/6/26 22:16
 */
@Data
public class RoleQueryDTO {

    private String roleName;

    private String roleCode;

    private BooleanFlag enable;

}
