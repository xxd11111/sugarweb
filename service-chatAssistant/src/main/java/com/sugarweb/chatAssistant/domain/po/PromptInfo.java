package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 提示词模板信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PromptInfo {
    @TableId
    private String promptId;

    private String agentId;

    /**
     * system, user
     */
    private String promptType;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<PromptVariableInfo> promptVariableList;
}
