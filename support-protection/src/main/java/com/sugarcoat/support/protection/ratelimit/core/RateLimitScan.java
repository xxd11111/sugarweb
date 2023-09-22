package com.sugarcoat.support.protection.ratelimit.core;

import cn.hutool.core.util.ArrayUtil;
import com.sugarcoat.protocol.protection.EnableRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @version 1.0
 * @Author Lmh
 * @Description TODO 扫描 并加载 配置
 * @CreateTime 2023-09-02 14:39
 */

@Slf4j
public class RateLimitScan implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 加载 rateLimit 注解配置
     */
    public void loadRateLimitConfig() {
        // 拿到包扫描范围
        EnableRateLimit bean = applicationContext.getBean(EnableRateLimit.class);
        String[] scanPackage = bean.baseScanPackage();
        if (ArrayUtil.isEmpty(scanPackage)) {
            SpringBootApplication application = applicationContext.getBean(SpringBootApplication.class);
            scanPackage = application.scanBasePackages();
        }

        // 查看这些包下哪些类是标记了 RateLimit注解的


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
