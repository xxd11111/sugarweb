package com.sugarweb.uims.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.dto.*;
import com.sugarweb.uims.entity.User;
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
public class UserService {

    public String save(UserSaveDto userSaveDto) {
        User user = new User();
        BeanUtils.copyProperties(user, userSaveDto);
        Db.save(user);
        return user.getId();
    }

    public String update(UserUpdateDto dto) {
        User user = Db.getById(dto.getId(), User.class);
        BeanUtils.copyProperties(user, dto);
        Db.save(user);
        return user.getId();
    }

    public UserDetailDto detail(String id) {
        User user = Optional.ofNullable(Db.getById(id, User.class))
                .orElseThrow(() -> new ValidateException("not find user"));
        UserDetailDto target = new UserDetailDto();
        BeanUtils.copyProperties(user, target);
        return target;
    }

    public IPage<UserPageVo> page(PageQuery pageQuery, UserQuery queryDto) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, queryDto.getUsername())
                .eq(User::getNickName, queryDto.getNickName())
                .eq(User::getMobilePhone, queryDto.getMobilePhone())
                .eq(User::getEnabled, queryDto.getEnable())
                .eq(User::getEmail, queryDto.getEmail());

        return Db.page(PageHelper.getPage(pageQuery), lambdaQueryWrapper)
                .convert(a -> {
                    UserPageVo userPageVo = new UserPageVo();
                    BeanUtils.copyProperties(a, userPageVo);
                    return userPageVo;
                });
    }

    public void modifyPassword(NewPasswordDto newPasswordDto) {
        String id = SecurityHelper.getLoginUser().getUserId();
        User user = Optional.ofNullable(Db.getById(id, User.class))
                .orElseThrow(() -> new ValidateException("not dind user"));
        String oldPassword = newPasswordDto.getOldPassword();
        if (!StrUtil.equals(user.getPassword(), oldPassword)) {
            throw new SecurityException("凭证错误");
        }
        user.setPassword(newPasswordDto.getNewPassword());
        Db.save(user);
    }

    public void remove(String id) {
        Db.removeById(id,User.class);
    }

    public boolean existUsername(String username) {
        return Db.count(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)) > 0;
    }

    public boolean existMobilePhone(String mobilePhone) {
        return Db.count(new LambdaQueryWrapper<User>()
                .eq(User::getMobilePhone, mobilePhone)) > 0;
    }


    public boolean existEmail(String email) {
        return Db.count(new LambdaQueryWrapper<User>()
                .eq(User::getMobilePhone, email)) > 0;
    }


}
