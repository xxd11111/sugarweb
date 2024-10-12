package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * StageMemory
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class AgentMemory {
    @TableId
    private String memoryId;

    private String agentId;

    private String stageId;

    private String sceneId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
