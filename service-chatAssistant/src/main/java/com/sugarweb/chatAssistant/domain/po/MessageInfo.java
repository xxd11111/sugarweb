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
public class MessageInfo {

    @TableId
    private String msgId;

    private String memoryId;

    private String content;

    /**
     * system: 系统消息 user: 用户消息 agent: 机器人消息
     */
    private String msgType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
