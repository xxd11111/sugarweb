package com.sugarweb.support.oss.api;

import java.time.LocalDateTime;

/**
 * 文件信息
 *
 * @author 许向东
 * @version 1.0
 */
public interface FileInfo {

    String getKey();

    String getFilename();

    String getFileType();

    long getFileSize();

    LocalDateTime getUploadTime();

    String getContentType();

    String getBizType();

}
