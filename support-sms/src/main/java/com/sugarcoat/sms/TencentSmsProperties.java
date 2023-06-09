package com.sugarcoat.sms;

/**
 * 腾讯sms属性
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/8
 */
public class TencentSmsProperties {

    /**
     * 配置节点
     * 阿里云 dysmsapi.aliyuncs.com
     * 腾讯云 sms.tencentcloudapi.com
     */
    private String endpoint = "sms.tencentcloudapi.com";

    /**
     * key
     */
    private String accessKeyId;

    /**
     * 密匙
     */
    private String accessKeySecret;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信应用ID (腾讯专属)
     */
    private String sdkAppId;
}
