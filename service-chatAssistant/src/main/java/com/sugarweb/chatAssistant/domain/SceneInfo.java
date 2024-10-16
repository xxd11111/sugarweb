package com.sugarweb.chatAssistant.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 场景信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class SceneInfo {

    @TableId
    private String sceneId;

    private String stageId;

    private String sceneName;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
