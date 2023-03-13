package com.xxd.sugarcoat.support.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-06
 */
@Slf4j
@Component
public class ServerApiRegister {
    @Resource
    private ServerApiRepository serverApiRepository;

    public void registry(Set<ServerApi> serverApis) {
        Set<ServerApiPO> apiEntities = serverApis.stream().map(api -> new ServerApiPO()).collect(Collectors.toSet());
        serverApiRepository.insert(apiEntities);
    }

}
