package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * PerformanceInfo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PerformanceInfo {

    @TableId
    private String performanceId;

    private String title;

    private String stageId;

    private String stageName;

    private String sceneId;

    private String sceneName;

    private String agentId;

    private String agentName;

    private String status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
