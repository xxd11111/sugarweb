package com.sugarcoat.support.parameter.domain;

import com.sugarcoat.support.parameter.ParamProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import java.util.Collection;

/**
 * 参数初始化
 *
 * @author xxd
 * @since 2023/9/5 22:09
 */
@RequiredArgsConstructor
@Slf4j
public class DefaultParamRunner implements ParamRunner {

    private final ParamProperties paramProperties;

    private final ParamScanner paramScanner;

    private final ParamRegistry paramRegistry;

    private final ParamCacheManager paramCacheManager;

    private final SgcParamRepository paramRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (paramProperties.isEnableRegister()){
            doRegister();
        }
        if (paramProperties.isEnableCache()){
            cacheReload();
        }
    }

    private void doRegister() {
        log.info("系统参数加载---开始");
        Collection<SugarcoatParameter> scan = paramScanner.scan();
        paramRegistry.register(scan);
        log.info("系统参数加载---结束");
    }

    private void cacheReload() {
        log.info("系统参数缓存加载---开始");
        Iterable<SugarcoatParameter> params = paramRepository.findAll();
        paramCacheManager.clean();
        for (SugarcoatParameter param : params) {
            paramCacheManager.put(param);
        }
        log.info("系统参数缓存加载---结束");
    }
}
