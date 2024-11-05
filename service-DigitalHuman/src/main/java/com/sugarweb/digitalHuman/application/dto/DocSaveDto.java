package com.sugarweb.digitalHuman.application.dto;

import com.sugarweb.oss.application.dto.FileDetailDto;
import com.sugarweb.oss.domain.po.FileInfo;
import lombok.Data;

/**
 * DocSaveDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DocSaveDto {

    private String kbId;

    private String docName;

    // 手动创建，文件上传
    private String sourceType;

    private FileDetailDto fileInfo;

}
