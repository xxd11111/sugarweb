package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天记忆
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ChatMemoryInfo {

    @TableId
    private String memoryId;

    private String userId;

    private String title;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
