package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 代理详情
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class AgentDetailDto {

    private String agentId;

    private String agentName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
