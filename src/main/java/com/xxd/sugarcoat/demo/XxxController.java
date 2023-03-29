package com.xxd.sugarcoat.demo;

import com.xxd.sugarcoat.support.prod.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

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

    @GetMapping("test3")
    public Result<Object> test4() throws IOException {
        File file = new File("D:\\临时测试\\aaaabbbb.txt");
        file.createNewFile();
        file.delete();
        return Result.ok();
    }
}
