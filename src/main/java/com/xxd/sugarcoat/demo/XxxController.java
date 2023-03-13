package com.xxd.sugarcoat.demo;

import com.xxd.sugarcoat.extend.uims.domain.model.user.UserRepository;
import com.xxd.sugarcoat.support.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-05
 */
@RestController
@RequestMapping("/xxx")
public class XxxController {
    @Resource
    HttpServletRequest httpServletRequest;

    @PostMapping("test1")
    public Result<Object> test1(){
        return Result.ok();
    }

    @DeleteMapping("test2")
    public Result<Object> test2(){
        return Result.ok();
    }

@Resource
    UserRepository userRepository;
    @GetMapping("test3")
    public Result<Object> test3(){
        httpServletRequest.getRequestURI();
        return Result.ok();
    }

    @PostMapping("test3")
    public Result<Object> test4(){
        return Result.ok();
    }
}
