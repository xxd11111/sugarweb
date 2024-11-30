package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import com.sugarweb.digitalHuman.infra.PromptUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AiActor
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Actor extends BaseEntity {

    @TableId
    private String actorId;

    private String actorName;

    private String promptTemplate;

    @TableField(exist = false)
    private String[] promptVariables;

    private String chatModelId;

    private String chatModeConfig;

    private String ttsModelId;

    private String ttsModelConfig;

    private String datasetId;

    public void parsePromptVar(){
        promptVariables = PromptUtil.parsePromptVariables(promptTemplate);
    }

}
