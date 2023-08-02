package com.sugarcoat.api.oss;

import java.time.LocalDateTime;

/**
 * 文件信息
 */
public interface FileInfo {

	String getFileGroup();

	String getFilePath();

	String getFileName();

	String getFileType();

	long getFileSize();

	LocalDateTime getUploadTime();

}