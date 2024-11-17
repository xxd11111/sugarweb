package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * PerformanceInfo
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StagePerformance extends BaseEntity {

    @TableId
    private String performanceId;

    private String title;

    private String stageId;

    private String stageName;

    private String scriptId;

    private String scriptName;

    private String actorId;

    private String actorName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
