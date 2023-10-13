package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.common.SgcRegister;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 接口注册
 *
 * @author xxd
 * @since 2022-11-17
 */
@Slf4j
@RequiredArgsConstructor
public class ApiRegister implements SgcRegister {

    private final WebApplicationContext applicationContext;

    private final SgcApiRepository apiRepository;

    private Map<String, SgcApi> apiMap = new HashMap<>();

    //todo 选取包 低优先级
    private final String[] basePackages = null;
    //todo 排除包 低优先级
    private final String[] excludePackages = null;

    public Map<String, SgcApi> scan() {
        //使用mappingHandlerMapping获取mvc已加载的接口
        Map<String, SgcApi> urlList = new HashMap<>();
        RequestMappingHandlerMapping bean = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = bean.getHandlerMethods();
        map.forEach((k, v) -> {
            //二次加载tag,operation接口
            Method method = v.getMethod();
            Class<?> clazz = method.getDeclaringClass();
            Tag tag = clazz.getAnnotation(Tag.class);
            if (tag == null) {
                return;
            }
            Operation operation = method.getAnnotation(Operation.class);
            if (operation == null) {
                return;
            }
            SgcApi sgcApi = new SgcApi();
            sgcApi.setTagName(tag.name());
            sgcApi.setTagDescription(tag.description());
            sgcApi.setOperationId(operation.operationId());
            sgcApi.setSummary(operation.summary());
            sgcApi.setOperationDescription(operation.description());

            PatternsRequestCondition patternsRequestCondition = k.getPatternsCondition();
            if (patternsRequestCondition == null) {
                return;
            }
            Set<String> patterns = patternsRequestCondition.getPatterns();
            if (patterns.size() > 1) {
                log.warn("ApiScanner扫描中发现存在多个Pattern:{}", patterns);
            }
            patterns.forEach(sgcApi::setUrl);
            RequestMethodsRequestCondition methodsRequestCondition = k.getMethodsCondition();
            Set<RequestMethod> methods = methodsRequestCondition.getMethods();
            if (methods.size() > 1) {
                log.warn("ApiScanner扫描中发现存在多个RequestMethod:{}", methods);
            }
            methods.forEach(a -> sgcApi.setRequestMethod(a.name()));
            urlList.put(sgcApi.getOperationId(), sgcApi);
        });

        log.info("ApiScanner扫描获取{}个接口信息", urlList.size());
        return urlList;
    }

    public void load(Map<String, SgcApi> apiMap) {
        this.apiMap.putAll(apiMap);
    }

    public void reload(Map<String, SgcApi> apiMap) {
        this.apiMap.clear();
        load(apiMap);
    }

    @Override
    public void register() {

        // 接口辅助功能是需要持久化的
        // todo api低优先级功能
        // 接口启用停用（用于异常问题出现时，部分功能主动停止）
        // 接口限流（需要接通protection模块）
        // 接口变动导致旧配置失效（需要在其他模块主动提醒）
        // 接口变动-----》项目启动立马加载，最终目标是要与运行项目中一致（故没必要持久化），通过operationId做识别

        Map<String, SgcApi> scan = getApiMap();
        Collection<SgcApi> values = scan.values();
        apiRepository.deleteAll();
        apiRepository.saveAll(values);
    }

    /**
     * 返回拷贝对象，目前浅拷贝就够用了
     */
    public Map<String, SgcApi> getApiMap() {
        Map<String, SgcApi> result = new HashMap<>();
        apiMap.forEach((k, v) -> result.put(k, v.clone()));
        return result;
    }

}
