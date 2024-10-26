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
public class KbDetailDto {

    private String kbId;

    private String kbName;

    private String collectionName;

    private String embeddingModel;

    private String dimension;

    private String status;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
