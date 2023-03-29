package com.xxd.sugarcoat.support.dev.server;

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
    private ServerApiScanner apiScanner;
    @Resource
    private ServerApiRegister apiRegister;

    @PostConstruct
    public void init(){
        autoRegistry();
    }

    public void autoRegistry() {
        //todo 实现动态更新条件，每次更新，有变化更新，不更新
        Set<ServerApi> apis = apiScanner.scanApi();
        apiRegister.registry(apis);
    }
}
