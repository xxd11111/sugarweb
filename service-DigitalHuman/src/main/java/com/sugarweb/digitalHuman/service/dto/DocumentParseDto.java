package com.sugarweb.digitalHuman.service.dto;

import lombok.Data;

import java.util.List;

/**
 * DocParseDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DocumentParseDto {

    private String datasetId;

    private List<String> documentIds;

}
