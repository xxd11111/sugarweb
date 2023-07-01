package com.sugarcoat.uims.application.dto;

import com.sugarcoat.api.common.BooleanFlag;
import lombok.Data;

/**
 * 角色分页vo
 *
 * @author xxd
 * @date 2022-12-29
 */
@Data
public class RolePageVo {

    private Long id;

    private String roleName;

    private String roleCode;

    private BooleanFlag enable;
}
