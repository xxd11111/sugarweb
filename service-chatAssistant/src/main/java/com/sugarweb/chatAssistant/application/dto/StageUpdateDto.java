package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;

/**
 * 舞台更新请求参数
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class StageUpdateDto {

    private String stageId;

    private String stageName;

    private String description;

}
