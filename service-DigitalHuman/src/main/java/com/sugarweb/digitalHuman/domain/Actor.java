package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
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

    private String chatModelId;

    private String chatModeConfig;

    private String ttsModelId;

    private String ttsModelConfig;

    private String datasetId;

}
