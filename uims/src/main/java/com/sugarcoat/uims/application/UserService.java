package com.sugarcoat.uims.application;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.uims.application.dto.*;
import com.sugarcoat.uims.application.vo.UserPageVo;
import com.sugarcoat.uims.application.vo.UserVo;

/**
 * 用户服务
 *
 * @author xxd
 * @since 2023-01-12
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
