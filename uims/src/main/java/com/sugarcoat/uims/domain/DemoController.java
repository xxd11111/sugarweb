package com.sugarcoat.uims.domain;

import com.sugarcoat.protocol.orm.BooleanEnum;
import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.support.orm.datapermission.DataPermissionContext;
import com.sugarcoat.support.orm.datapermission.DataPermissionInfo;
import com.sugarcoat.support.orm.tenant.TenantContext;
import com.sugarcoat.support.orm.tenant.TenantDS;
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
@TenantDS
public class DemoController {

    @Resource
    private DemoRepo demoRepo;

    @GetMapping("/list")
    public Result list(Boolean tenantIgnore, String dsId){
        DataPermissionInfo dataPermissionInfo = new DataPermissionInfo();
        dataPermissionInfo.setOrgId("1");
        DataPermissionContext.setDataPermissionInfo(dataPermissionInfo);
        TenantContext.setTenantIgnore(tenantIgnore);
        Iterable<DemoDo> all = demoRepo.findAll();
        return Result.data(all);
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

    @GetMapping("/list2")
    public Result list2(Boolean tenantIgnore, String dsId){
        TenantContext.setTenantIgnore(tenantIgnore);
        Iterable<DemoDo> all = demoRepo.findAll();
        return Result.data(all);
    }

    @PostMapping("/save2")
    public Result save2() {
        DemoDo demoDo = new DemoDo();
        demoDo.setId(UUID.randomUUID().toString().replace("-", ""));
        demoDo.setName("name");
        demoDo.setStatus(BooleanEnum.TRUE);
        demoRepo.save(demoDo);
        return Result.ok();
    }
}
