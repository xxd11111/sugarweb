package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.RoleDTO;
import com.sugarcoat.uims.application.dto.RoleVO;
import com.sugarcoat.uims.application.dto.RolePageVO;
import com.sugarcoat.uims.application.dto.RoleQueryDTO;

import java.util.List;

/**
 * 角色服务
 *
 * @author xxd
 * @date 2022-12-29
 */
public interface RoleService {

    String save(RoleDTO roleDTO);

    void modify(RoleDTO roleDTO);

    void remove(String id);

    void associateMenu(List<String> menuIds);

    PageData<RolePageVO> page(RoleQueryDTO roleQueryDTO);

    RoleVO find(String id);

}
