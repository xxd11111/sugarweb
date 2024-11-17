package com.sugarweb.digitalHuman.application.dto;

import lombok.Data;


/**
 * kb更新参数
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DatasetUpdateDto {

    private String datasetId;

    private String datasetName;

    private String status;

    private String description;

}
