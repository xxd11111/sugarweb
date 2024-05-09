package com.sugarweb.uims.domain.dto;

import com.sugarweb.framework.common.BooleanFlag;
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

    private BooleanFlag enable;

}
