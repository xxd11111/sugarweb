package com.sugarweb.chatAssistant.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.chatAssistant.constans.ChatRole;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ChatMessage
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ChatMsg {

    @TableId
    private String msgId;

    private String memoryId;

    private String content;

    /**
     * system: 系统消息 user: 用户消息 assistant: 机器人消息
     */
    private String chatRole;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String userId;

    public static ChatMsg of(ChatRole chatRole, String prompt, String memoryId) {
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setMemoryId(memoryId);
        chatMsg.setContent(prompt);
        chatMsg.setChatRole(chatRole.getCode());
        LocalDateTime now = LocalDateTime.now();
        chatMsg.setCreateTime(now);
        chatMsg.setUpdateTime(now);
        return chatMsg;
    }


}
