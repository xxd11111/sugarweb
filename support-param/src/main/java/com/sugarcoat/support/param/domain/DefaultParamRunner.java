package com.sugarcoat.support.param.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;

import java.util.Collection;

/**
 * 参数初始化
 *
 * @author xxd
 * @since 2023/9/5 22:09
 */
@RequiredArgsConstructor
public class DefaultParamRunner implements ParamRunner {

    private final ParamScanner paramScanner;

    private final ParamRegistry paramRegistry;

    @Override
    public void run(ApplicationArguments args) {
        Collection<SugarcoatParam> scan = paramScanner.scan();
        paramRegistry.register(scan);
    }
}
