package com.sugarweb.chatAssistant.application.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class KbSaveDto {

    private String kbName;

    private String embeddingModel;

    private String status;

    private String description;

}
