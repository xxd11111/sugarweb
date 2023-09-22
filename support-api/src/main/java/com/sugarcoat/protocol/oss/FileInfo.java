package com.sugarcoat.protocol.oss;

import java.time.LocalDateTime;

/**
 * 文件信息
 *
 * @author 许向东
 * @date 2023/9/15
 */
public interface FileInfo {

    String getFileGroup();

    String getKey();

    String getFileName();

    String getFileType();

    long getFileSize();

    LocalDateTime getUploadTime();

    String getContentType();

}
