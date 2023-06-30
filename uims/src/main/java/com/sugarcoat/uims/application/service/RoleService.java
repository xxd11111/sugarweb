package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.RoleDto;
import com.sugarcoat.uims.application.dto.RoleVo;
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

    String save(RoleDto roleDTO);

    void modify(RoleDto roleDTO);

    void remove(String id);

    void associateMenu(List<String> menuIds);

    PageData<RolePageVO> page(RoleQueryDTO roleQueryDTO);

    RoleVo find(String id);

}
