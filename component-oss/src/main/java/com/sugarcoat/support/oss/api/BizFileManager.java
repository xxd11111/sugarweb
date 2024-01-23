package com.sugarcoat.support.oss.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 文件业务绑定服务
 *
 * @author xxd
 * @version 1.0
 */
public interface BizFileManager {

	void associateBizFile(String fileGroup, String bizId, Collection<String> fileIds);

	void associateBizFile(String fileGroup, String bizId, String fileId);

	void separateByBizId(String bizId, String fileGroup);

	void separateByBizId(String bizId);

	void separateByFileId(String fileId);

	void separateByFileId(Collection<String> fileIds);

	List<FileInfo> listFile(String bizId, String fileGroup);

	List<FileInfo> listFile(String bizId);

	Optional<FileInfo> getTopFile(String bizId, String fileGroup);

	Optional<FileInfo> getFile(String fileId);

}
