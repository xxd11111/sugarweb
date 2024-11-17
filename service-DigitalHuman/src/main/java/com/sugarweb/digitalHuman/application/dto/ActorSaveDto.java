package com.sugarweb.digitalHuman.application.dto;

import lombok.Data;

/**
 * actor保存参数
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ActorSaveDto {

    private String actorName;

    private String chatModelId;

}
