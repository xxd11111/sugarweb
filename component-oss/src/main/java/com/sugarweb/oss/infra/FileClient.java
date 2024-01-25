package com.sugarweb.oss.infra;

import com.sugarweb.oss.domain.FileInfo;

import java.io.InputStream;

/**
 * 文件客户端
 *
 * @author xxd
 * @version 1.0
 */
public interface FileClient {

	void createBucket();

	FileUploadResult upload(String path, InputStream inputStream, String contentType);

	InputStream getContent(String key);

	FileInfo getFileObject(String key);

	void delete(String key);

}