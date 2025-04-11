package com.sugarweb.digitalHuman.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 舞台详情
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class StageDetailDto {

    private String stageId;

    private String stageName;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
