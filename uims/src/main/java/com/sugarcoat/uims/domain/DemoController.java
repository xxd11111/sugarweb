package com.sugarcoat.uims.domain;

import com.sugarcoat.protocol.common.BooleanEnum;
import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.support.orm.TenantContext;
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
    public Result list(Boolean ignore){
        TenantContext.setTenantId(true);
        Iterable<DemoDo> all1 = demoRepo.findAll();
        TenantContext.setTenantId(false);
        Iterable<DemoDo> all2 = demoRepo.findAll();
        return Result.data(all1);
    }

    @PostMapping("/save")
    public Result save() {
        DemoDo demoDo = new DemoDo();
        demoDo.setId(UUID.randomUUID().toString().replace("-", ""));
        demoDo.setName("name");
        demoDo.setStatus(BooleanEnum.TRUE);
        demoRepo.save(demoDo);
        return Result.ok();
    }
}
