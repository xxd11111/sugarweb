package com.sugarcoat.uims.application.mapper;

import com.sugarcoat.uims.application.dto.RoleDto;
import com.sugarcoat.uims.application.vo.RolePageVo;
import com.sugarcoat.uims.application.vo.RoleVo;
import com.sugarcoat.uims.domain.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 角色mapper
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/30
 */
@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "menus", ignore = true)
    Role roleDtoToRole(RoleDto roleDto);

    @Mapping(target = "menus", ignore = true)
    void updateRole(RoleDto roleDto, @MappingTarget Role role);

    RolePageVo roleToRolePageVo(Role role);

    @Mapping(target = "menus", ignore = true)
    RoleVo roleToRoleVo(Role role);

}
