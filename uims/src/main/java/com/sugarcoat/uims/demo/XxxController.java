package com.sugarcoat.uims.demo;

import com.sugarcoat.api.common.Result;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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

	@GetMapping("test1")
	public Result<Object> test1() {
		httpServletRequest.setAttribute("a", "b");
		return Result.ok();
	}

	@DeleteMapping("test2")
	public Result<Object> test2() {
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
