package com.sugarweb.uims.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.R;
import com.sugarweb.framework.security.LoginUser;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.uims.application.dto.PasswordLoginDto;
import com.sugarweb.uims.domain.RoleMenu;
import com.sugarweb.uims.domain.User;
import com.sugarweb.uims.domain.UserRole;
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
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticateController {

    @PostMapping("login")
    public R login(PasswordLoginDto passwordLoginDto) {
        User user = Db.getOne(ChainWrappers.lambdaQueryChain(User.class)
                .eq(User::getUsername, passwordLoginDto.getAccount())
                .getWrapper());
        if (user == null) {
            throw new SecurityException("用户或密码错误");
        }
        boolean checkpw = BCrypt.checkpw(passwordLoginDto.getPassword(), user.getPassword());
        if (!checkpw) {
            throw new SecurityException("用户或密码错误");
        }
        StpUtil.login(user.getId());
        List<UserRole> userRoles = Db.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        List<RoleMenu> roleMenus = Db.list(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, userRoles.stream().map(UserRole::getRoleId).toList()));
        LoginUser loginUser = new LoginUser(user.getId(),
                user.getUsername(),
                userRoles.stream().map(UserRole::getRoleCode).toList(),
                roleMenus.stream().map(RoleMenu::getApiPermission).distinct().toList());
        StpUtil.getSession().set("userInfo", loginUser);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return R.data(tokenInfo);
    }

    @GetMapping("userInfo")
    public R userInfo() {
        LoginUser loginUser = SecurityHelper.getLoginUser();
        return R.data(loginUser);
    }

    @GetMapping("tokenInfo")
    public R tokenInfo() {
        return R.data(StpUtil.getTokenInfo());
    }

    @PostMapping("logout")
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }

    @PostMapping("kickout/{id}")
    public R logout(@PathVariable String id) {
        StpUtil.kickout(id);
        return R.ok();
    }

}
