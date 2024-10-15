package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AiAgent
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class AgentInfo {

    @TableId
    private String agentId;

    @Schema(description = "")
    private String agentName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
