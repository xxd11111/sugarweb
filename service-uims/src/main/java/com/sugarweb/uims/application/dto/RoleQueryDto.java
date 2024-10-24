package com.sugarweb.uims.application.dto;

import com.sugarweb.framework.common.Flag;
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

    private Flag enable;

}
