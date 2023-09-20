package com.sugarcoat.api.server;

/**
 * 服务器信息服务
 *
 * @author xxd
 * @since 2023/9/20 23:02
 */
public interface ServerService {
    /**
     * 获取服务器运行信息
     * @return 服务器运行信息
     */
    Object runtimeInfo();

    /**
     * 获取服务器内存信息
     * @return 服务器内存信息
     */
    Object memoryInfo();

    /**
     * 获取服务器cpu信息
     * @return 服务器cpu信息
     */
    Object cpuInfo();

    /**
     * 获取服务器网络信息
     * @return 服务器网络信息
     */
    Object networkInfo();

    /**
     * 获取服务器磁盘信息
     * @return 服务器磁盘信息
     */
    Object diskInfo();
    
}
