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
    private String messageId;

    private String memoryId;

    private String content;

    private String messageType;

    private String sendUserId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
