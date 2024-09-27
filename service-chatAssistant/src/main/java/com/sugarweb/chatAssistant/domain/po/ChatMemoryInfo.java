package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ChatSession
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class ChatMemoryInfo {

    @TableId
    private String memoryId;

    private String userId;

    private String createTime;

    private String updateTime;

}
