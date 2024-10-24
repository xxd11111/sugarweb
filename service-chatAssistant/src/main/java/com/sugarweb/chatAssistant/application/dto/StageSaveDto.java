package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;

/**
 * 舞台新增请求参数
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class StageSaveDto {

    private String stageName;

    private String description;

}
