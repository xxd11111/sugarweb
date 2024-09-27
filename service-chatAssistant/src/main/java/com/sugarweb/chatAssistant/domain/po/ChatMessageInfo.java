package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ChatMessage
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class ChatMessageInfo {

    @TableId
    private String messageId;

    private String memoryId;

    private String messageType;

    private String content;

    private String createTime;

    private String updateTime;

}
