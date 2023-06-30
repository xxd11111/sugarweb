package com.sugarcoat.uims.application.service;

import com.sugarcoat.uims.application.dto.RoleDto;
import com.sugarcoat.uims.application.dto.RoleVo;
import com.sugarcoat.uims.domain.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色mapper
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/30
 */
@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role roleDtoToRole(RoleDto roleDto);

    RoleDto roleToRoleDto(Role role);

    RoleVo roleToRoleVo(Role role);

}
