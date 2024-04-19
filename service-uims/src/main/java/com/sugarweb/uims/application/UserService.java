package com.sugarweb.uims.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.uims.domain.dto.NewPasswordDto;
import com.sugarweb.uims.domain.dto.UserDto;
import com.sugarweb.uims.domain.dto.UserQueryDto;
import com.sugarweb.uims.domain.dto.UserPageVo;
import com.sugarweb.uims.domain.dto.UserVo;

/**
 * 用户服务
 *
 * @author xxd
 * @version 1.0
 */
public interface UserService {

    String save(UserDto userDTO);

    UserVo findOne(String id);

    IPage<UserPageVo> page(PageQuery pageQuery, UserQueryDto userQueryDto);

    void modifyPassword(NewPasswordDto newPasswordDto);

    void remove(String id);

    boolean existUsername(String username);

    boolean existMobilePhone(String mobilePhone);

    boolean existEmail(String email);

}
