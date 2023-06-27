package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.NewPasswordDTO;
import com.sugarcoat.uims.application.dto.UserDTO;
import com.sugarcoat.uims.application.dto.UserQueryVO;

/**
 * 用户服务
 *
 * @author xxd
 * @date 2023-01-12
 */
public interface UserService {

    String save(UserDTO userDTO);

    UserDTO find(String id);

    PageData<UserDTO> page(UserQueryVO userQueryVO);

    void modifyPassword(NewPasswordDTO newPasswordDTO);

    void remove(String id);

    boolean existUsername(String username);

    boolean existPhoneNumber(String phoneNumber);

    boolean existEmail(String email);

}
