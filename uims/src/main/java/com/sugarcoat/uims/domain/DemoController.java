package com.sugarcoat.uims.domain;

import com.sugarcoat.protocol.common.BooleanFlag;
import com.sugarcoat.protocol.common.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/10/26
 */
@RestController
@RequestMapping("/demotest")
public class DemoController {

    @Resource
    private DemoRepo demoRepo;

    @GetMapping("/list")
    public Result list(){
        Iterable<DemoDo> all = demoRepo.findAll();
        return Result.data(all);
    }

    @PostMapping("/save")
    public Result save(){
        DemoDo demoDo = new DemoDo();
        demoDo.setId(UUID.randomUUID().toString().replace("-",""));
        demoDo.setName("name");
        demoDo.setStatus(BooleanFlag.TRUE);
        demoRepo.save(demoDo);
        return Result.ok();
    }
}
