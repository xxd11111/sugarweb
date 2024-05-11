package com.sugarweb.uims.domain.dto;

import com.sugarweb.framework.common.Flag;
import lombok.Data;

/**
 * 角色分页vo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class RolePageVo {

    private Long id;

    private String roleName;

    private String roleCode;

    private Flag enable;
}
