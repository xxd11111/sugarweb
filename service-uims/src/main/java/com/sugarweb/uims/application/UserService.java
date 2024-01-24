package com.sugarweb.uims.application;

import com.sugarweb.common.PageData;
import com.sugarweb.uims.application.dto.*;
import com.sugarweb.uims.application.vo.UserPageVo;
import com.sugarweb.uims.application.vo.UserVo;

/**
 * 用户服务
 *
 * @author xxd
 * @version 1.0
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
