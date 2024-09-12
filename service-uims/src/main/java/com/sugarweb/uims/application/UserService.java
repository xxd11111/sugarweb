package com.sugarweb.uims.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.application.dto.NewPasswordDto;
import com.sugarweb.uims.application.dto.UserDto;
import com.sugarweb.uims.application.dto.UserQuery;
import com.sugarweb.uims.application.dto.UserPageVo;
import com.sugarweb.uims.application.dto.UserVo;
import com.sugarweb.uims.domain.User;
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

    public String save(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(user, userDto);
        Db.save(user);
        return user.getId();
    }

    public UserVo findOne(String id) {
        User user = Optional.ofNullable(Db.getById(id, User.class))
                .orElseThrow(() -> new ValidateException("not find user"));
        UserVo target = new UserVo();
        BeanUtils.copyProperties(user, target);
        return target;
    }

    public IPage<UserPageVo> page(PageQuery pageQuery, UserQuery queryDto) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, queryDto.getUsername())
                .eq(User::getNickName, queryDto.getNickName())
                .eq(User::getMobilePhone, queryDto.getMobilePhone())
                .eq(User::getStatus, queryDto.getEnable())
                .eq(User::getEmail, queryDto.getEmail());

        return Db.page(PageHelper.getPage(pageQuery), lambdaQueryWrapper)
                .convert(a -> {
                    UserPageVo userPageVo = new UserPageVo();
                    BeanUtils.copyProperties(a, userPageVo);
                    return userPageVo;
                });
    }

    public void modifyPassword(NewPasswordDto newPasswordDto) {
        String id = SecurityHelper.getLoginUser().getId();
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
