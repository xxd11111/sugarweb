package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * StageInfo
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

    private String agentId;

    @TableField(exist = false)
    private AgentInfo agentInfo;

    private String sceneId;

    @TableField(exist = false)
    private SceneInfo sceneInfo;

    /**
     * 0未表演 1表演中
     */
    private String status;

    private String performanceId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
