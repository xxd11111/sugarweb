package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.NewPasswordDTO;
import com.sugarcoat.uims.application.dto.UserDto;
import com.sugarcoat.uims.application.dto.UserQueryDTO;
import com.sugarcoat.uims.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author xxd
 * @date 2023/6/27 23:16
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String save(UserDto userDTO) {
        return null;
    }

    @Override
    public UserDto find(String id) {
        return null;
    }

    @Override
    public PageData<UserDto> page(UserQueryDTO userQueryDTO) {
        return null;
    }

    @Override
    public void modifyPassword(NewPasswordDTO newPasswordDTO) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public boolean existUsername(String username) {
        return false;
    }

    @Override
    public boolean existPhoneNumber(String phoneNumber) {
        return false;
    }

    @Override
    public boolean existEmail(String email) {
        return false;
    }
}
