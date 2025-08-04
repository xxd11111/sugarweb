package com.sugarweb.uims.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.R;
import com.sugarweb.framework.exception.ServiceException;
import com.sugarweb.framework.security.LoginUser;
import com.sugarweb.uims.dto.PasswordLoginDto;
import com.sugarweb.uims.entity.RoleMenu;
import com.sugarweb.uims.entity.User;
import com.sugarweb.uims.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录认证管理
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("login")
    public R login(PasswordLoginDto passwordLoginDto) {
        User user = Db.getOne(ChainWrappers.lambdaQueryChain(User.class)
                .eq(User::getUsername, passwordLoginDto.getAccount())
                .getWrapper());
        if (user == null) {
            throw new ServiceException("用户名或密码错误");
        }
        boolean checkpw = BCrypt.checkpw(passwordLoginDto.getPassword(), user.getPassword());
        if (!checkpw) {
            throw new ServiceException("用户名或密码错误");
        }
        StpUtil.login(user.getId());
        List<UserRole> userRoles = Db.list(new LambdaQueryWrapper<>(UserRole.class)
                .eq(UserRole::getUserId, user.getId()));
        List<RoleMenu> roleMenus;
        if (!userRoles.isEmpty()){
            roleMenus = Db.list(new LambdaQueryWrapper<>(RoleMenu.class)
                    .in(RoleMenu::getRoleId, userRoles.stream().map(UserRole::getRoleId).toList()));
        }else {
            roleMenus = new ArrayList<>();
        }

        LoginUser loginUser = new LoginUser(user.getId(),
                user.getUsername(),
                userRoles.stream().map(UserRole::getRoleCode).toList(),
                roleMenus.stream().map(RoleMenu::getApiPermission).distinct().toList());
        StpUtil.getSession().set("userInfo", loginUser);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return R.data(tokenInfo);
    }

    @PostMapping("logout")
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }

}
