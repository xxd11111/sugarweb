package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.*;

/**
 * 用户服务
 *
 * @author xxd
 * @date 2023-01-12
 */
public interface UserService {

    String save(UserDto userDTO);

    UserVo find(String id);

    PageData<UserPageVo> page(UserQueryDto userQueryDto);

    void modifyPassword(NewPasswordDto newPasswordDto);

    void remove(String id);

    boolean existUsername(String username);

    boolean existMobilePhone(String mobilePhone);

    boolean existEmail(String email);

}
