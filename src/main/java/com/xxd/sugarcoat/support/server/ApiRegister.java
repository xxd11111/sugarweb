package com.xxd.sugarcoat.support.server;

import com.xxd.sugarcoat.extend.uims.domain.model.api.Api;
import com.xxd.sugarcoat.extend.uims.domain.model.api.ApiRepository;
import com.xxd.sugarcoat.extend.uims.infrastructure.entity.ApiPO;
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
public class ApiRegister {
    @Resource
    private ApiRepository apiRepository;

    public void registry(Set<Api> apis) {
        //todo 实现动态更新条件，每次更新，有变化更新，不更新
        //todo 实现convert
        Set<ApiPO> apiEntities = apis.stream().map(api -> new ApiPO()).collect(Collectors.toSet());
        apiRepository.insert(apiEntities);
    }

}
