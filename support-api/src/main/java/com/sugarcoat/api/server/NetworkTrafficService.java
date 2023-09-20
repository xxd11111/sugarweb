package com.sugarcoat.api.server;

/**
 * 流量统计服务
 *
 * @author xxd
 * @since 2023/9/20 23:15
 */
public interface NetworkTrafficService {

    /**
     * 获取接口流量信息
     *
     * @return 接口流量信息
     */
    Object apiNetworkTraffic(String apiId);

    /**
     * 获取接口流量历史
     *
     * @return 接口流量历史
     */
    Object apiNetworkTrafficHistory(String apiId);

    /**
     * 获取应用流量信息
     *
     * @return 应用流量历史
     */
    Object appNetworkTraffic();

    /**
     * 获取应用流量历史
     *
     * @return 应用流量历史
     */
    Object appNetworkTrafficHistory();

}
