package com.sugarweb.oss.service.dto;

import com.sugarweb.oss.entity.FileInfo;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class FileConvert {
    public static FileDetailDto toDto(FileInfo fileInfo) {
        return FileDetailDto.builder()
                .fileId(fileInfo.getFileId())
                .groupCode(fileInfo.getGroupCode())
                .fileKey(fileInfo.getFileKey())
                .filename(fileInfo.getFilename())
                .fileType(fileInfo.getFileSuffix())
                .contentType(fileInfo.getContentType())
                .fileSize(fileInfo.getFileSize())
                .uploadTime(fileInfo.getCreateTime())
                .build();
    }

}
