package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;

/**
 * 场景新增传输对象
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class SceneSaveDto {

    private String stageId;

    private String sceneName;

    private String description;

}
