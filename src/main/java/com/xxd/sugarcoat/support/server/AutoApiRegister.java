package com.xxd.sugarcoat.support.server;

import com.xxd.sugarcoat.extend.uims.domain.model.api.Api;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2023-01-03
 */
//@Component
public class AutoApiRegister {

    @Resource
    private ApiScanner apiScanner;
    @Resource
    private ApiRegister apiRegister;

    @PostConstruct
    public void init(){
        autoRegistry();
    }

    public void autoRegistry() {
        //todo 实现动态更新条件，每次更新，有变化更新，不更新
        Set<Api> apis = apiScanner.scanApi();
        apiRegister.registry(apis);
    }
}
