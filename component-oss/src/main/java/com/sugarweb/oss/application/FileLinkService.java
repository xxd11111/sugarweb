package com.sugarweb.oss.application;

import com.sugarweb.oss.domain.FileInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 文件业务绑定服务
 *
 * @author xxd
 * @version 1.0
 */
public interface FileLinkService {

	void linkFiles(String bizId, String fileGroup, Collection<String> fileIds);

	void linkFile(String bizId, String fileGroup, String fileId);

	void breakFiles(String bizId, String fileGroup);

	void breakFiles(String bizId);

	void breakByFileId(String fileId);

	void breakByFileIds(Collection<String> fileIds);

	List<FileInfo> findAll(String bizId, String fileGroup);

	List<FileInfo> findAll(String bizId);

	Optional<FileInfo> findFirstOne(String bizId, String fileGroup);

	Optional<FileInfo> findFirstOne(String fileId);

}
