package com.sugarweb.chatAssistant.controller;

import lombok.Data;

import java.time.LocalDateTime;

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
