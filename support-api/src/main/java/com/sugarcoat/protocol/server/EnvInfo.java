package com.sugarcoat.protocol.server;

/**
 * 服务器信息服务
 *
 * @author xxd
 * @since 2023/9/20 23:02
 */
public interface EnvInfo {

    /**
     * 获取服务器运行信息
     * @return 服务器运行信息
     */
    Object getRuntimeInfo();

    /**
     * 获取服务器内存信息
     * @return 服务器内存信息
     */
    Object getMemoryInfo();

    /**
     * 获取服务器cpu信息
     * @return 服务器cpu信息
     */
    Object getCpuInfo();

    /**
     * 获取服务器网络信息
     * @return 服务器网络信息
     */
    Object getNetworkInfo();

    /**
     * 获取服务器磁盘信息
     * @return 服务器磁盘信息
     */
    Object getDiskInfo();
    
}
