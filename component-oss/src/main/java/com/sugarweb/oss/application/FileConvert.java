package com.sugarweb.oss.application;

import com.sugarweb.oss.po.FileInfo;

/**
 * TODO
 *
 * @author 许向东
 * @version 1.0
 */
public class FileConvert {
    public static FileDto toDto(FileInfo fileInfo) {
        return FileDto.builder()
                .fileId(fileInfo.getFileId())
                .groupCode(fileInfo.getGroupCode())
                .fileKey(fileInfo.getFileKey())
                .filename(fileInfo.getFilename())
                .fileType(fileInfo.getFileType())
                .contentType(fileInfo.getContentType())
                .fileSize(fileInfo.getFileSize())
                .uploadTime(fileInfo.getUploadTime())
                .build();
    }

}