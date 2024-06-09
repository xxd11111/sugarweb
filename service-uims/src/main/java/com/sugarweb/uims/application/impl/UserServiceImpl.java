package com.sugarweb.uims.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.application.UserService;
import com.sugarweb.uims.domain.dto.NewPasswordDto;
import com.sugarweb.uims.domain.dto.UserDto;
import com.sugarweb.uims.domain.dto.UserQuery;
import com.sugarweb.uims.domain.dto.UserPageVo;
import com.sugarweb.uims.domain.dto.UserVo;
import com.sugarweb.uims.domain.User;
import com.sugarweb.uims.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户服务实现类
 *
 * @author xxd
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String save(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(user, userDto);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public UserVo findOne(String id) {
        User user = Optional.ofNullable(userRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("not find user"));
        UserVo target = new UserVo();
        BeanUtils.copyProperties(user, target);
        return target;
    }

    @Override
    public IPage<UserPageVo> page(PageQuery pageQuery, UserQuery queryDto) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, queryDto.getUsername())
                .eq(User::getNickName, queryDto.getNickName())
                .eq(User::getMobilePhone, queryDto.getMobilePhone())
                .eq(User::getStatus, queryDto.getEnable())
                .eq(User::getEmail, queryDto.getEmail());

        return userRepository.selectPage(PageHelper.getPage(pageQuery), lambdaQueryWrapper)
                .convert(a -> {
                    UserPageVo userPageVo = new UserPageVo();
                    BeanUtils.copyProperties(a, userPageVo);
                    return userPageVo;
                });
    }

    @Override
    public void modifyPassword(NewPasswordDto newPasswordDto) {
        String id = SecurityHelper.getUserInfo().getId();
        User user = Optional.ofNullable(userRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("not dind user"));
        String oldPassword = newPasswordDto.getOldPassword();
        user.checkCertificate(oldPassword);
        user.modifyPassword(newPasswordDto.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public void remove(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existUsername(String username) {
        return userRepository.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
    }

    @Override
    public boolean existMobilePhone(String mobilePhone) {
        return userRepository.exists(new LambdaQueryWrapper<User>()
                .eq(User::getMobilePhone, mobilePhone));
    }

    @Override
    public boolean existEmail(String email) {
        return userRepository.exists(new LambdaQueryWrapper<User>()
                .eq(User::getMobilePhone, email));
    }
}
