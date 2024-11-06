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

    @TableField(exist = false)
    private PromptTemplateInfo systemPrompt;

    private String chatModelId;

    @TableField(exist = false)
    private ModelInfo chatModelInfo;

    private String kbId;

    @TableField(exist = false)
    private KbInfo kbInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
