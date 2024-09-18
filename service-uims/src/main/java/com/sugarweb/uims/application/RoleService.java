package com.sugarweb.uims.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.application.dto.RoleDto;
import com.sugarweb.uims.application.dto.RoleVo;
import com.sugarweb.uims.application.dto.RolePageVo;
import com.sugarweb.uims.application.dto.RoleQueryDto;
import com.sugarweb.uims.domain.Menu;
import com.sugarweb.uims.domain.Role;
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
public class RoleService {
    public String save(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        Db.save(role);
        return role.getId();
    }


    public void remove(String id) {
        Db.removeById(id, Role.class);
    }


    public void associateMenu(String id, List<String> menuIds) {
        Role role = Optional.ofNullable(Db.getById(id, Role.class))
                .orElseThrow(() -> new ValidateException("not find role"));
        List<Menu> menus = Db.listByIds(menuIds, Menu.class);
        Db.save(role);
    }


    public IPage<RolePageVo> page(PageQuery pageQuery, RoleQueryDto dto) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getStatus, dto.getEnable())
                .like(Role::getRoleName, dto.getRoleName())
                .eq(Role::getRoleCode, dto.getRoleCode());

        return Db.page(PageHelper.getPage(pageQuery), lambdaQueryWrapper)
                .convert(a -> {
                    RolePageVo rolePageVo = new RolePageVo();
                    BeanUtils.copyProperties(a, rolePageVo);
                    return rolePageVo;
                });
    }


    public RoleVo find(String id) {
        Role role = Optional.ofNullable(Db.getById(id, Role.class))
                .orElseThrow(() -> new ValidateException("not find role"));
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        return roleVo;
    }
}
