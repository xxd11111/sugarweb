package com.xxd.sugarcoat.extend.uims.domain.model.role;

import com.xxd.sugarcoat.extend.uims.domain.model.menu.Menu;
import com.xxd.sugarcoat.support.status.AccessibleEnum;
import lombok.Data;

import java.util.Collection;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-07
 */
@Data
public class Role {
    private Long id;

    private String roleName;

    private String roleCode;

    private Collection<Menu> menus;

    private AccessibleEnum status;

}
