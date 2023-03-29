package com.xxd.sugarcoat.support.dev.log.access;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xxd
 * @description 接口调用日志
 * @date 2022-10-27
 */
@Data
public class AccessLog {
    /**
     * 日志 id
     */
    private String id;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 服务ip
     */
    private String serverIp;
    /**
     * 请求url
     */
    private String requestUrl;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 结果码
     */
    private Integer resultCode;
    /**
     * 结果提示
     */
    private String resultMsg;
    /**
     * 用户 id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;
    /**
     * 开始请求时间
     */
    private LocalDateTime beginTime;
    /**
     * 结束请求时间
     */
    private LocalDateTime endTime;
    /**
     * 执行时长，单位：毫秒
     */
    private Integer duration;

}
