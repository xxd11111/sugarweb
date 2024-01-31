package com.sugarweb.uims.application.impl;

import com.google.common.base.Strings;
import com.google.common.collect.TreeMultimap;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.application.dto.*;
import com.sugarweb.uims.application.UserService;
import com.sugarweb.uims.application.vo.UserPageVo;
import com.sugarweb.uims.application.vo.UserVo;
import com.sugarweb.uims.domain.user.QUser;
import com.sugarweb.uims.domain.user.User;
import com.sugarweb.uims.domain.user.UserRepository;
import com.sugarweb.framework.orm.ExpressionWrapper;
import com.sugarweb.framework.orm.PageDataConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(id).orElseThrow(() -> new ValidateException("not find user"));
        UserVo target = new UserVo();
        BeanUtils.copyProperties(user, target);
        return target;
    }

    @Override
    public UserVo findLoginUserInfo(String id) {
        return null;
    }

    @Override
    public PageData<UserPageVo> page(UserQueryDto userQueryDto) {
        BooleanExpression expression = ExpressionWrapper.of()
                .and(!Strings.isNullOrEmpty(userQueryDto.getUsername()), QUser.user.username.like(userQueryDto.getUsername(), '/'))
                .and(!Strings.isNullOrEmpty(userQueryDto.getEmail()), QUser.user.email.like(userQueryDto.getEmail(), '/'))
                .and(!Strings.isNullOrEmpty(userQueryDto.getMobilePhone()), QUser.user.mobilePhone.like(userQueryDto.getMobilePhone(), '/'))
                .and(!Strings.isNullOrEmpty(userQueryDto.getNickName()), QUser.user.nickName.like(userQueryDto.getNickName(), '/'))
                .and(userQueryDto.getEnable() != null, QUser.user.enable.eq(userQueryDto.getEnable()))
                .and(userQueryDto.getAccountType() != null, QUser.user.accountType.eq(userQueryDto.getAccountType()))
                .build();

        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<UserPageVo> page = userRepository.findAll(expression, pageRequest)
                .map(a->{
                    UserPageVo userPageVo = new UserPageVo();
                    BeanUtils.copyProperties(a, userPageVo);
                    return userPageVo;
                });
        return PageDataConvert.convert(page, UserPageVo.class);
    }

    @Override
    public void modifyPassword(NewPasswordDto newPasswordDto) {
        String id = SecurityHelper.getUserInfo().getId();
        User user = userRepository.findById(id).orElseThrow(() -> new ValidateException("not dind user"));
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
        return userRepository.exists(QUser.user.username.eq(username));
    }

    @Override
    public boolean existMobilePhone(String mobilePhone) {
        return userRepository.exists(QUser.user.mobilePhone.eq(mobilePhone));
    }

    @Override
    public boolean existEmail(String email) {
        return userRepository.exists(QUser.user.email.eq(email));
    }
}
