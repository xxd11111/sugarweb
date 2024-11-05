package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class AgentInfo {

    @TableId
    private String agentId;

    private String agentName;

    private String systemPromptId;

    private String chatModelId;

    @TableField(exist = false)
    private PromptTemplateInfo systemPrompt;

    @TableField(exist = false)
    private ModelInfo chatModelInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
