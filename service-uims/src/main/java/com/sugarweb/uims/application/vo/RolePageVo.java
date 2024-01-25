package com.sugarweb.uims.application.vo;

import com.sugarweb.framework.orm.BooleanEnum;
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

    private BooleanEnum enable;
}
