package com.xxd.sugarcoat.demo;

import com.xxd.sugarcoat.extend.uims.domain.model.user.UserRepository;
import com.xxd.sugarcoat.support.common.R;
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
    public R<Object> test1(){
        return R.ok();
    }

    @DeleteMapping("test2")
    public R<Object> test2(){
        return R.ok();
    }

@Resource
    UserRepository userRepository;
    @GetMapping("test3")
    public R<Object> test3(){
        httpServletRequest.getRequestURI();
        return R.ok();
    }

    @PostMapping("test3")
    public R<Object> test4(){
        return R.ok();
    }
}
