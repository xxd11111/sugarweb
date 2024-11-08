package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    private String sceneName;

    private String systemPromptId;

    @TableField(exist = false)
    private PromptTemplateInfo systemPrompt;

    private String description;

    @TableField(exist = false)
    private List<SceneTopic> sceneTopicList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
