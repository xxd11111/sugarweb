package com.sugarweb.uims.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.sugarweb.framework.common.R;
import com.sugarweb.uims.domain.dto.PasswordLoginDto;
import com.sugarweb.uims.domain.dto.TokenVo;
import com.sugarweb.uims.application.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8889/authenticate/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    // 查询 Token 信息  ---- http://localhost:8889/authenticate/tokenInfo
    @RequestMapping("tokenInfo")
    public R tokenInfo() {
        return R.data(StpUtil.getTokenInfo());
    }

    // 测试注销  ---- http://localhost:8889/authenticate/logout
    @RequestMapping("logout")
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }

}
