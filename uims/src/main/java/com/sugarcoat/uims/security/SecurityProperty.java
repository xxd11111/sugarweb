package com.sugarcoat.uims.security;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * 安全配置
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/25
 */
@Data
public class SecurityProperty {
    /**
     * 登录账号限制
     */
    private int accountOnlineNum = -1;

    /**
     * 登录抢占规则
     */
    private int controlToken = 0;

    /**
     * 登录时间限制
     */
    private int accountOnlineLimit = 0;

    private LocalTime onlineStartTime;

    private LocalTime onlineEndTime;

    /**
     * ip限制
     */
    private int accountIpLimit = 0;

    private List<String> allowIps;

    private List<String> forbidIps;

    private long rememberMe;

    private long refreshTime;

    private long activeTime;

}
