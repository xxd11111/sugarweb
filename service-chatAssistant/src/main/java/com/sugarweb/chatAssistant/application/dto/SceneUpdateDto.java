package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;

/**
 * 场景更新传输对象
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class SceneUpdateDto {

    private String sceneId;

    private String stageId;

    private String sceneName;

    private String description;

}
