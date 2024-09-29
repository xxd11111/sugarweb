package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AiAgent
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class AiAgent {

    @TableId
    private String agentId;

    private String agentName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
