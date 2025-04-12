package com.sugarweb.digitalHuman.service.dto;

import com.sugarweb.oss.service.dto.FileDetailDto;
import lombok.Data;

/**
 * DocSaveDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DocSaveDto {

    private String datasetId;

    private String documentName;

    // 手动创建，文件上传
    private String sourceType;

    private FileDetailDto fileInfo;

}
