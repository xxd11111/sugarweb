package com.sugarcoat.uims.application.vo;

import com.sugarcoat.protocol.common.BooleanEnum;
import lombok.Data;

/**
 * 角色分页vo
 *
 * @author xxd
 * @since 2022-12-29
 */
@Data
public class RolePageVo {

    private Long id;

    private String roleName;

    private String roleCode;

    private BooleanEnum enable;
}
