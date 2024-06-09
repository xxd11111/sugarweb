package com.sugarweb.uims.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.domain.dto.RoleDto;
import com.sugarweb.uims.domain.dto.RoleVo;
import com.sugarweb.uims.domain.dto.RolePageVo;
import com.sugarweb.uims.domain.dto.RoleQueryDto;
import com.sugarweb.uims.application.RoleService;
import com.sugarweb.uims.domain.Menu;
import com.sugarweb.uims.domain.repository.MenuRepository;
import com.sugarweb.uims.domain.Role;
import com.sugarweb.uims.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 角色服务实现类
 *
 * @author xxd
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;

    @Override
    public String save(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        roleRepository.save(role);
        return role.getId();
    }

    @Override
    public void remove(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void associateMenu(String id, List<String> menuIds) {
        Role role = Optional.ofNullable(roleRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("not find role"));
        List<Menu> menus = menuRepository.selectBatchIds(menuIds);
        role.setMenus(menus);
        roleRepository.save(role);
    }

    @Override
    public IPage<RolePageVo> page(PageQuery pageQuery, RoleQueryDto dto) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getStatus, dto.getEnable())
                .like(Role::getRoleName, dto.getRoleName())
                .eq(Role::getRoleCode, dto.getRoleCode());

        return roleRepository.selectPage(PageHelper.getPage(pageQuery), lambdaQueryWrapper)
                .convert(a -> {
                    RolePageVo rolePageVo = new RolePageVo();
                    BeanUtils.copyProperties(a, rolePageVo);
                    return rolePageVo;
                });
    }

    @Override
    public RoleVo find(String id) {
        Role role = Optional.ofNullable(roleRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("not find role"));
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        return roleVo;
    }
}
