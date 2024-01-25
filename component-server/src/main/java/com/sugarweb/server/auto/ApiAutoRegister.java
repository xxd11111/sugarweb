package com.sugarweb.server.auto;

import com.sugarweb.framework.auto.AbstractAutoRegistry;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.server.domain.ApiInfo;
import com.sugarweb.server.domain.ApiInfoRepository;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 接口注册
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class ApiAutoRegister extends AbstractAutoRegistry<ApiInfo> {

    private final WebApplicationContext applicationContext;

    private final ApiInfoRepository apiInfoRepository;

    private final Map<String, ApiInfo> apiMap = new HashMap<>();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //todo 选取包 低优先级
    private final String[] basePackages = null;
    //todo 排除包 低优先级
    private final String[] excludePackages = null;

    public Map<String, ApiInfo> doScan() {
        //使用mappingHandlerMapping获取mvc已加载的接口
        Map<String, ApiInfo> urlList = new HashMap<>();
        RequestMappingHandlerMapping bean = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = bean.getHandlerMethods();
        map.forEach((k, v) -> {
            //二次加载tag,operation接口
            Method method = v.getMethod();
            Class<?> clazz = method.getDeclaringClass();
            ApiInfo apiInfo = new ApiInfo();
            Tag tag = clazz.getAnnotation(Tag.class);
            if (tag == null) {
                return;
            } else {
                apiInfo.setTagName(tag.name());
                apiInfo.setTagDescription(tag.description());
            }
            Operation operation = method.getAnnotation(Operation.class);
            if (operation == null) {
                return;
            } else {
                if (operation.operationId() == null || operation.operationId().isEmpty()) {
                    throw new FrameworkException("{}#{}:未指定operationId", clazz.getSimpleName(), method.getName());
                }
                apiInfo.setOperationId(operation.operationId());
                apiInfo.setSummary(operation.summary());
                apiInfo.setOperationDescription(operation.description());
            }
            PatternsRequestCondition patternsRequestCondition = k.getPatternsCondition();
            if (patternsRequestCondition == null) {
                return;
            }
            Set<String> patterns = patternsRequestCondition.getPatterns();
            if (patterns.size() > 1) {
                log.warn("ApiScanner扫描中发现存在多个Pattern:{}", patterns);
            }
            patterns.forEach(apiInfo::setUrl);
            RequestMethodsRequestCondition methodsRequestCondition = k.getMethodsCondition();
            Set<RequestMethod> methods = methodsRequestCondition.getMethods();
            if (methods.size() > 1) {
                log.warn("ApiScanner扫描中发现存在多个RequestMethod:{}", methods);
            }
            methods.forEach(a -> apiInfo.setRequestMethod(a.name()));
            if (urlList.containsKey(apiInfo.getOperationId())) {
                throw new FrameworkException("存在重复operationId,{}", apiInfo.getOperationId());
            } else {
                urlList.put(apiInfo.getOperationId(), apiInfo);
            }
        });
        log.info("ApiScanner扫描获取{}个接口信息", urlList.size());
        urlList.forEach((k, v) -> log.info("ApiScanner扫描接口：{}", v));
        return urlList;
    }

    public void load(Map<String, ApiInfo> apiMap) {
        Lock lock = readWriteLock.writeLock();
        if (lock.tryLock()) {
            try {
                this.apiMap.putAll(apiMap);
            } finally {
                lock.unlock();
            }
        } else {
            throw new FrameworkException("ApiRegister:load操作写锁超时");
        }
    }

    public void reload(Map<String, ApiInfo> apiMap) {
        this.apiMap.clear();
        load(apiMap);
    }

    public void doRegister() {

        // 接口辅助功能是需要持久化的
        // todo api低优先级功能
        // 接口启用停用（用于异常问题出现时，部分功能主动停止）
        // 接口限流（需要接通protection模块）
        // 接口变动导致旧配置失效（需要在其他模块主动提醒）
        // 接口变动-----》项目启动立马加载，最终目标是要与运行项目中一致（故没必要持久化），通过operationId做识别

        Map<String, ApiInfo> scan = getApiMap();
        Collection<ApiInfo> values = scan.values();
        apiInfoRepository.deleteAll();
        apiInfoRepository.saveAll(values);
    }

    /**
     * 返回拷贝对象，目前浅拷贝就够用了
     */
    public Map<String, ApiInfo> getApiMap() {
        Map<String, ApiInfo> result = new HashMap<>();
        Lock lock = readWriteLock.readLock();
        if (lock.tryLock()) {
            try {
                apiMap.forEach((k, v) -> result.put(k, v.clone()));
            } finally {
                lock.unlock();
            }
        } else {
            throw new FrameworkException("ApiRegister#getApiMap操作读锁超时");
        }
        return result;
    }

    @Override
    protected void insert(ApiInfo o) {

    }

    @Override
    protected void merge(ApiInfo db, ApiInfo scan) {

    }

    @Override
    protected void deleteByCondition(Collection<ApiInfo> collection) {

    }

    @Override
    protected ApiInfo selectOne(ApiInfo o) {
        return null;
    }

    @Override
    public Collection<ApiInfo> scan() {
        return null;
    }

}
