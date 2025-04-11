package com.sugarweb.digitalHuman.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 场景详情
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ScriptDetailDto {

    private String scriptId;

    private String scriptName;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
