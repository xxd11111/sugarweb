package com.sugarweb.oss.application;

import com.sugarweb.oss.domain.FileInfo;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

/**
 * 文件存储服务
 *
 * @author xxd
 * @version 1.0
 */
public interface FileService {

	FileInfo upload(String fileGroup, InputStream inputStream, String contentType, String filename);

	InputStream findContent(String fileId);

	Optional<FileInfo> findOne(String fileId);

	void remove(String fileId);

	void remove(Collection<String> fileIds);

}
