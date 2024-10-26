package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;


/**
 * kb更新参数
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class KbUpdateDto {

    private String kbId;

    private String kbName;

    private String embeddingModel;

    private String status;

    private String description;

}
