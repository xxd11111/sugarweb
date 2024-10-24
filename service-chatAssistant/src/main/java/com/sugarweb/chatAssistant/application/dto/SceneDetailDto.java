package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 场景详情
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class SceneDetailDto {

    private String sceneId;

    private String stageId;

    private String sceneName;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
