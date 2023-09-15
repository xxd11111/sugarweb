package com.sugarcoat.api.oss;

import java.io.InputStream;

/**
 * 文件客户端
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/5
 */
public interface FileManager {

	void createBucket();

	void upload(String path, InputStream inputStream, String contentType);

	InputStream getContent(String key);

	FileInfo getFileObject(String key);

	void delete(String key);

}