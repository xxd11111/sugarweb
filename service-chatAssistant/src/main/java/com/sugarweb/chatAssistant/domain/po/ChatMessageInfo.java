package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.chatAssistant.agent.ability.ChatRole;
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
    private String msgId;

    private String memoryId;

    private String content;

    /**
     * system: 系统消息 user: 用户消息 agent: 机器人消息
     */
    private String chatRole;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static ChatMessageInfo of(ChatRole chatRole, String prompt, String memoryId) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setMemoryId(memoryId);
        chatMessageInfo.setContent(prompt);
        chatMessageInfo.setChatRole(chatRole.getCode());
        LocalDateTime now = LocalDateTime.now();
        chatMessageInfo.setCreateTime(now);
        chatMessageInfo.setUpdateTime(now);
        return chatMessageInfo;
    }


}
