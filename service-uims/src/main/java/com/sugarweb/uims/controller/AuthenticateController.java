package com.sugarweb.uims.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.R;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.framework.security.UserInfo;
import com.sugarweb.uims.domain.User;
import com.sugarweb.uims.domain.repository.UserRepository;
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
    // 测试登录，浏览器访问： http://localhost:8889/authenticate/doLogin?username=zhang&password=123456

    UserRepository userRepository;

    @PostMapping("login")
    public R doLogin(String username, String password) {
        User user = Db.getOne(ChainWrappers.lambdaQueryChain(User.class)
                .eq(User::getUsername, username)
                .getWrapper());
        if (user == null) {
            throw new SecurityException("用户或密码错误");
        }
        String salt = user.getSalt();
        boolean checkpw = BCrypt.checkpw(password, salt);
        if (!checkpw) {
            throw new SecurityException("用户或密码错误");
        }
        StpUtil.login(user.getId());
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), new ArrayList<>(), new ArrayList<>());
        StpUtil.getSession().set("userInfo", userInfo);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return R.data(tokenInfo);
    }

    @GetMapping("userInfo")
    public R userInfo() {
        UserInfo userInfo = SecurityHelper.getUserInfo();
        return R.data(userInfo);
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

    @RequestMapping("kickout/{id}")
    public R logout(@PathVariable String id) {
        StpUtil.kickout(id);
        return R.ok();
    }

}
