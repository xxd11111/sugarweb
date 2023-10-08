package com.sugarcoat.support.server.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 接口扫描者 todo 重写
 *
 * @author xxd
 * @since 2022-11-17
 */
@Slf4j
public class ApiScanner {

    private RequestMappingHandlerMapping mappingHandlerMapping;

    private String[] basePackages;

    public Set<SgcApi> scan() {
        // 方式1
		//获取basePackages下存在@Operation注解的接口apis
		//获取apis的类上@Tag注解信息，缺失则取类名

		//方式2 不建议 使用mappingHandlerMapping获取mvc已加载的接口

        Set<SgcApi> urlList = new HashSet<>();
        Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = map.keySet();
        for (RequestMappingInfo info : requestMappingInfos) {
            SgcApi sgcApi = new SgcApi();
            PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
            Set<String> patterns = patternsRequestCondition.getPatterns();
            log.info("url:{}", patterns);
            for (String url : patterns) {
                sgcApi.setUrl(url);
            }
            RequestMethodsRequestCondition methodsRequestCondition = info.getMethodsCondition();
            Set<RequestMethod> methods = methodsRequestCondition.getMethods();
            log.info("method:{}", methods);
            for (RequestMethod method : methods) {
                String type = method.name();
                sgcApi.setMethodType(type);
            }
            sgcApi.setRemark(info.toString());
            urlList.add(sgcApi);
        }
        log.info(String.valueOf(urlList));
        return urlList;
    }

}
