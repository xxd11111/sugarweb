package com.sugarcoat.support.oss.api;

import java.time.LocalDateTime;

/**
 * 文件信息
 *
 * @author 许向东
 * @date 2023/9/15
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
