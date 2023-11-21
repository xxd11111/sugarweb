package com.sugarcoat.uims.application.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.support.orm.PageDataConvert;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.orm.ExpressionWrapper;
import com.sugarcoat.uims.application.dto.RoleDto;
import com.sugarcoat.uims.application.vo.RoleVo;
import com.sugarcoat.uims.application.vo.RolePageVo;
import com.sugarcoat.uims.application.dto.RoleQueryDto;
import com.sugarcoat.uims.application.RoleService;
import com.sugarcoat.uims.domain.menu.Menu;
import com.sugarcoat.uims.domain.menu.MenuRepository;
import com.sugarcoat.uims.domain.role.QRole;
import com.sugarcoat.uims.domain.role.Role;
import com.sugarcoat.uims.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色服务实现类
 *
 * @author xxd
 * @since 2023/6/27 23:16
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;

    @Override
    public String save(RoleDto roleDto) {
        Role role = new Role();
        BeanUtil.copyProperties(roleDto, role);
        roleRepository.save(role);
        return role.getId();
    }

    @Override
    public void modify(RoleDto roleDto) {
        Role role = roleRepository.findById(roleDto.getId()).orElseThrow(() -> new ValidateException("not find role"));
        BeanUtil.copyProperties(roleDto, role);
        roleRepository.save(role);
    }

    @Override
    public void remove(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void associateMenu(String id, List<String> menuIds) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ValidateException("not find role"));
        List<Menu> menus = new ArrayList<>();
        menuRepository.findAllById(menuIds).forEach(menus::add);
        role.setMenus(menus);
        roleRepository.save(role);
    }

    @Override
    public PageData<RolePageVo> page(RoleQueryDto dto) {
        BooleanExpression expression = ExpressionWrapper.of()
                .and(StrUtil.isNotEmpty(dto.getRoleCode()), QRole.role.roleCode.like(dto.getRoleCode(), '/'))
                .and(StrUtil.isNotEmpty(dto.getRoleName()), QRole.role.roleName.like(dto.getRoleName(), '/'))
                .and(dto.getEnable() != null, QRole.role.enable.eq(dto.getEnable()))
                .build();

        PageRequest pageable = PageRequest.of(1, 10);
        Page<RolePageVo> page = roleRepository.findAll(expression, pageable)
                .map(a->{
                    RolePageVo rolePageVo = new RolePageVo();
                    BeanUtil.copyProperties(a, rolePageVo);
                    return rolePageVo;
                });
        return PageDataConvert.convert(page, RolePageVo.class);
    }

    @Override
    public RoleVo find(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ValidateException("not find role"));
        RoleVo roleVo = new RoleVo();
        BeanUtil.copyProperties(role, roleVo);
        return roleVo;
    }
}
