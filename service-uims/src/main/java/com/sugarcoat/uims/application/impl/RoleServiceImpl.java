package com.sugarcoat.uims.application.impl;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xxd.common.PageData;
import com.xxd.exception.ValidateException;
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
import com.xxd.orm.ExpressionWrapper;
import com.xxd.orm.PageDataConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void modify(RoleDto roleDto) {
        Role role = roleRepository.findById(roleDto.getId()).orElseThrow(() -> new ValidateException("not find role"));
        BeanUtils.copyProperties(roleDto, role);
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
                .and(!Strings.isNullOrEmpty(dto.getRoleCode()), QRole.role.roleCode.like(dto.getRoleCode(), '/'))
                .and(!Strings.isNullOrEmpty(dto.getRoleName()), QRole.role.roleName.like(dto.getRoleName(), '/'))
                .and(dto.getEnable() != null, QRole.role.enable.eq(dto.getEnable()))
                .build();

        PageRequest pageable = PageRequest.of(1, 10);
        Page<RolePageVo> page = roleRepository.findAll(expression, pageable)
                .map(a->{
                    RolePageVo rolePageVo = new RolePageVo();
                    BeanUtils.copyProperties(a, rolePageVo);
                    return rolePageVo;
                });
        return PageDataConvert.convert(page, RolePageVo.class);
    }

    @Override
    public RoleVo find(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ValidateException("not find role"));
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        return roleVo;
    }
}
