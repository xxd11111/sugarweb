package com.xxd.sugarcoat.support.dev.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-17
 */
@Slf4j
@Component
public class ServerApiScanner {

    @Resource
    private RequestMappingHandlerMapping mappingHandlerMapping;

    public Set<ServerApi> scanApi() {
        Set<ServerApi> urlList = new HashSet<>();
        Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = map.keySet();
        for (RequestMappingInfo info : requestMappingInfos) {
            ServerApi serverApi = new ServerApi();
            PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
            Set<String> patterns = patternsRequestCondition.getPatterns();
            log.info("url:{}", patterns);
            for (String url : patterns) {
                serverApi.setUrl(url);
            }
            RequestMethodsRequestCondition methodsRequestCondition = info.getMethodsCondition();
            Set<RequestMethod> methods = methodsRequestCondition.getMethods();
            log.info("method:{}", methods);
            for (RequestMethod method : methods) {
                String type = method.name();
                serverApi.setMethodType(type);
            }
            serverApi.setRemark(info.toString());
            urlList.add(serverApi);
        }
        log.info(String.valueOf(urlList));
        return urlList;
    }

}
