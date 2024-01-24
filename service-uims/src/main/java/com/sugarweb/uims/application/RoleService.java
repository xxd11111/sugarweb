package com.sugarweb.uims.application;

import com.sugarweb.common.PageData;
import com.sugarweb.uims.application.dto.RoleDto;
import com.sugarweb.uims.application.vo.RoleVo;
import com.sugarweb.uims.application.vo.RolePageVo;
import com.sugarweb.uims.application.dto.RoleQueryDto;

import java.util.List;

/**
 * 角色服务
 *
 * @author xxd
 * @version 1.0
 */
public interface RoleService {

    String save(RoleDto roleDTO);

    void modify(RoleDto roleDTO);

    void remove(String id);

    void associateMenu(String id, List<String> menuIds);

    PageData<RolePageVo> page(RoleQueryDto roleQueryDTO);

    RoleVo find(String id);

}
