package com.sugarcoat.api.server;

/**
 * 应用信息服务
 *
 * @author xxd
 * @since 2023/9/20 23:09
 */
public interface ApplicationService {

    /**
     * 获取应用运行信息
     * @return 应用运行信息
     */
    Object runtimeInfo();

    /**
     * 获取jvm内存信息
     * @return jvm内存信息
     */
    Object jvmMemoryInfo();

    /**
     * 获取jvm配置信息
     * @return jvm配置信息
     */
    Object jvmConfigInfo();

}
