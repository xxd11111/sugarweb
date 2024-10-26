package com.sugarweb.chatAssistant.controller;

import com.sugarweb.oss.application.FileDto;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DocDetailDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DocDetailDto {

    private String docId;

    private String kbId;

    private String docName;

    private FileDto fileInfo;

    private String docType;

    private String docSize;

    private String sourceType;

    private String docStatus;

    private String parseStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
