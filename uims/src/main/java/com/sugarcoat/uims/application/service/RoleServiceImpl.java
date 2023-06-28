package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.RoleDTO;
import com.sugarcoat.uims.application.dto.RoleVO;
import com.sugarcoat.uims.application.dto.RolePageVO;
import com.sugarcoat.uims.application.dto.RoleQueryDTO;
import com.sugarcoat.uims.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色服务实现类
 *
 * @author xxd
 * @date 2023/6/27 23:16
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public String save(RoleDTO roleDTO) {
        return null;
    }

    @Override
    public void modify(RoleDTO roleDTO) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public void associateMenu(List<String> menuIds) {

    }

    @Override
    public PageData<RolePageVO> page(RoleQueryDTO roleQueryDTO) {
        return null;
    }

    @Override
    public RoleVO find(String id) {
        return null;
    }
}
