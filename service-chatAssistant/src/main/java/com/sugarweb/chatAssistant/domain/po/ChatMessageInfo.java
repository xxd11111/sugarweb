package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ChatMessage
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ChatMessageInfo {

    @TableId
    private String chatId;

    private String memoryId;

    private String content;

    /**
     * system: 系统消息 user: 用户消息 agent: 机器人消息
     */
    private String chatRole;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
