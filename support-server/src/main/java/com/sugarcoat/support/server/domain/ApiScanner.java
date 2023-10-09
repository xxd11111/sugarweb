package com.sugarcoat.support.server.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import com.sugarcoat.protocol.param.InnerParamGroup;
import com.sugarcoat.support.server.controller.ApiController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 接口扫描者 todo 重写
 *
 * @author xxd
 * @since 2022-11-17
 */
@Slf4j
public class ApiScanner {

    private RequestMappingHandlerMapping mappingHandlerMapping;

    private String basePackage;

    public Collection<SgcApi> scan() {
        // 方式1 不建议
		//获取basePackages下存在@Tag,@Operation注解的接口apis  问题点：与mvc加载逻辑需要一致

        //获取包下class
        // Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Tag.class);
        // if (CollUtil.isEmpty(classes)) {
        //     return new ArrayList<>();
        // }
        // Collection<SgcApi> apis = new ArrayList<>();
        // //遍历class
        // for (Class<?> clazz : classes) {
        //     Tag tag = clazz.getAnnotation(Tag.class);
        //     String name = tag.name();
        //     String tagDescription = tag.description();
        //     RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
        //
        //     Method[] methods = clazz.getMethods();
        //     for (Method method : methods) {
        //         Operation annotation = method.getAnnotation(Operation.class);
        //         String operationId = annotation.operationId();
        //         String summary = annotation.summary();
        //         String operationDescription = annotation.description();
        //         RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
        //         RequestMethod[] requestMethods = methodRequestMapping.method();
        //         for (RequestMethod requestMethod : requestMethods) {
        //             String httpMethodName = requestMethod.asHttpMethod().name();
        //         }
        //
        //     }
        // }

		//方式2 使用mappingHandlerMapping获取mvc已加载的接口

        Set<SgcApi> urlList = new HashSet<>();
        Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();
        map.forEach((k,v)->{
            //二次加载mvc接口
            Operation annotation = v.getMethod().getAnnotation(Operation.class);
        });

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
