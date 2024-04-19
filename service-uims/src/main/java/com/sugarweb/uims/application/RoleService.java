package com.sugarweb.uims.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.uims.domain.dto.RoleDto;
import com.sugarweb.uims.domain.dto.RoleVo;
import com.sugarweb.uims.domain.dto.RolePageVo;
import com.sugarweb.uims.domain.dto.RoleQueryDto;

import java.util.List;

/**
 * 角色服务
 *
 * @author xxd
 * @version 1.0
 */
public interface RoleService {

    String save(RoleDto roleDto);

    void remove(String id);

    void associateMenu(String id, List<String> menuIds);

    IPage<RolePageVo> page(PageQuery pageQuery, RoleQueryDto roleQueryDTO);

    RoleVo find(String id);

}
