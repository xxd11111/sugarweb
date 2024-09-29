package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * StageMemory
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class StageMemory {
    @TableId
    private String id;

    private String memoryId;
    
    private String stageId;
    
    private String userId;
    
}
