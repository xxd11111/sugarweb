package com.sugarcoat.support.server.domain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
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
@RequiredArgsConstructor
public class ApiScanner {

    private final WebApplicationContext applicationContext;;

    private String basePackage;

    @PostConstruct
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
        RequestMappingHandlerMapping bean = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = bean.getHandlerMethods();
        map.forEach((k, v) -> {
            SgcApi sgcApi = new SgcApi();
            //二次加载tag,operation接口
            Method method = v.getMethod();
            Class<?> clazz = method.getDeclaringClass();
            Tag tag = clazz.getAnnotation(Tag.class);
            if (tag != null){
                sgcApi.setTagName(tag.name());
                sgcApi.setTagDescription(tag.description());
            }
            Operation operation = method.getAnnotation(Operation.class);
            if (operation != null){
                String operationId = operation.operationId();
                String summary = operation.summary();
                String description = operation.description();
                sgcApi.setOperationId(operationId);
                sgcApi.setSummary(summary);
                sgcApi.setOperationDescription(description);
            }
            PatternsRequestCondition patternsRequestCondition = k.getPatternsCondition();
            Set<String> patterns = patternsRequestCondition.getPatterns();
            log.info("url:{}", patterns);
            for (String url : patterns) {
                sgcApi.setUrl(url);
            }
            RequestMethodsRequestCondition methodsRequestCondition = k.getMethodsCondition();
            Set<RequestMethod> methods = methodsRequestCondition.getMethods();
            log.info("method:{}", methods);
            for (RequestMethod requestMethod : methods) {
                String name = requestMethod.name();
                sgcApi.setRequestMethod(name);
            }
            urlList.add(sgcApi);
        });

        log.info(String.valueOf(urlList));
        return urlList;
    }

}
