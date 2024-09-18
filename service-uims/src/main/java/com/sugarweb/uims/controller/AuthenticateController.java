package com.sugarweb.uims.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.R;
import com.sugarweb.framework.security.LoginUser;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.uims.domain.User;
import com.sugarweb.uims.application.dto.PasswordLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 认证控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticateController {

    public static void main(String[] args) {
        String gensalt = BCrypt.gensalt(10);
        System.out.println(gensalt);
        System.out.println(BCrypt.hashpw("123456", gensalt));
    }

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
        LoginUser loginUser = new LoginUser(user.getId(), user.getUsername(), new ArrayList<>(), new ArrayList<>());
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

    @RequestMapping("logout")
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }

    @RequestMapping("kickOut/{id}")
    public R logout(@PathVariable String id) {
        StpUtil.kickout(id);
        return R.ok();
    }

}
