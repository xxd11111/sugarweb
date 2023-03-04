package com.xxd.sugarcoat.support.server;

import com.xxd.sugarcoat.extend.uims.domain.model.api.Api;
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
public class ApiScanner {

    @Resource
    private RequestMappingHandlerMapping mappingHandlerMapping;

    public Set<Api> scanApi() {
        Set<Api> urlList = new HashSet<>();
        Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = map.keySet();
        for (RequestMappingInfo info : requestMappingInfos) {
            Api api = new Api();
            PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
            Set<String> patterns = patternsRequestCondition.getPatterns();
            if (patterns.size() > 1) log.error("有两个url,{}", info);
            for (String url : patterns) {
                api.setUrl(url);
            }
            RequestMethodsRequestCondition methodsRequestCondition = info.getMethodsCondition();
            Set<RequestMethod> methods = methodsRequestCondition.getMethods();
            if (methods.size() > 1) log.error("有两个method,{}", info);
            for (RequestMethod method : methods) {
                String type = method.name();
                api.setMethodType(type);
            }
            api.setRemark(info.toString());
            urlList.add(api);
        }
        log.info(String.valueOf(urlList));
        return urlList;
    }

}
