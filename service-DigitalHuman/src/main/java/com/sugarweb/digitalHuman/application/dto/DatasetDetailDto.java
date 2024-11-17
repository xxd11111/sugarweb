package com.sugarweb.digitalHuman.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * KbDetailDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DatasetDetailDto {

    private String datasetId;

    private String datasetName;

    private String collectionName;

    private String embeddingModel;

    private Integer dimension;

    private String status;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
