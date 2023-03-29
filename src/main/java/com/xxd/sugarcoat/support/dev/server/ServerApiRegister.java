package com.xxd.sugarcoat.support.dev.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-06
 */
@Slf4j
@Component
public class ServerApiRegister {
//    @Resource
    private ServerApiRepository serverApiRepository;

    public void registry(Set<ServerApi> serverApis) {
        serverApiRepository.saveAll(serverApis);
    }

}
