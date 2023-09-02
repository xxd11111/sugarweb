package com.sugarcoat.uims.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.RoleDto;
import com.sugarcoat.uims.application.vo.RoleVo;
import com.sugarcoat.uims.application.vo.RolePageVo;
import com.sugarcoat.uims.application.dto.RoleQueryDto;

import java.util.List;

/**
 * 角色服务
 *
 * @author xxd
 * @since 2022-12-29
 */
public interface RoleService {

    String save(RoleDto roleDTO);

    void modify(RoleDto roleDTO);

    void remove(String id);

    void associateMenu(String id, List<String> menuIds);

    PageData<RolePageVo> page(RoleQueryDto roleQueryDTO);

    RoleVo find(String id);

}
