package com.sugarcoat.support.oss.api;

import java.io.InputStream;

/**
 * 文件客户端
 *
 * @author xxd
 * @version 1.0
 */
public interface FileClient {

	void createBucket();

	UploadInfo upload(String path, InputStream inputStream, String contentType);

	InputStream getContent(String key);

	FileInfo getFileObject(String key);

	void delete(String key);

}