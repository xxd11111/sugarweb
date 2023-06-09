package com.sugarcoat.api.oss;

import java.time.LocalDateTime;

/**
 * 文件信息
 */
public interface FileInfo {

    String getFileGroup();

    String getFileUrl();

    String getFileName();

    String getFileType();

    String getFileSize();

    LocalDateTime getUploadTime();

}