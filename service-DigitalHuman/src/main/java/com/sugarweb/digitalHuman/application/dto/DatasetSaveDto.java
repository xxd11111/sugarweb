package com.sugarweb.digitalHuman.application.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DatasetSaveDto {

    private String datasetName;

    private String embeddingModelId;

    private String description;

}
