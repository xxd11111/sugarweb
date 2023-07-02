package com.sugarcoat.uims.application.service;

import com.sugarcoat.uims.application.dto.UserDto;
import com.sugarcoat.uims.application.dto.UserPageVo;
import com.sugarcoat.uims.application.dto.UserVo;
import com.sugarcoat.uims.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 用户mapper
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/30
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);


    @Mapping(target = "roles", ignore = true)
    UserVo userToUserVo(User user);

    UserPageVo userToUserPageVo(User user);

}
