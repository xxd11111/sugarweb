package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.NewPasswordDTO;
import com.sugarcoat.uims.application.dto.UserDto;
import com.sugarcoat.uims.application.dto.UserQueryDTO;

/**
 * 用户服务
 *
 * @author xxd
 * @date 2023-01-12
 */
public interface UserService {

    String save(UserDto userDTO);

    UserDto find(String id);

    PageData<UserDto> page(UserQueryDTO userQueryDTO);

    void modifyPassword(NewPasswordDTO newPasswordDTO);

    void remove(String id);

    boolean existUsername(String username);

    boolean existPhoneNumber(String phoneNumber);

    boolean existEmail(String email);

}
