package com.sugarweb.chatAssistant.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 舞台信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class StageInfo {

    @TableId
    private String stageId;

    private String stageName;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
