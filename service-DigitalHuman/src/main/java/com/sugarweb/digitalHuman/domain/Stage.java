package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * StageInfo
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Stage extends BaseEntity {

    @TableId
    private String stageId;

    private String stageName;

    private String description;

    private String actorId;

    @TableField(exist = false)
    private Actor actor;

    private String scriptId;

    @TableField(exist = false)
    private Script script;

    /**
     * 0未表演 1表演中
     */
    private String status;

    private String performanceId;

    @TableField(exist = false)
    private StagePerformance stagePerformance;

}
