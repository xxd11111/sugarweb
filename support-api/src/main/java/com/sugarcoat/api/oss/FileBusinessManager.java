package com.sugarcoat.api.oss;

import java.util.Collection;
import java.util.List;

/**
 * 文件业务绑定服务
 *
 * @author xxd
 * @date 2023/6/2
 */
public interface FileBusinessManager {

	void associateFile(String associationId, String fileGroup, List<String> fileIds);

	void separateFile(String associationId, String fileGroup);

	void separateAllFile(String associationId);

	void separateFileId(String fileId);

	void separateFileId(Collection<String> fileIds);

	List<FileInfo> listAssociationFile(String associationId, String fileGroup);

	List<FileInfo> listAllAssociationFile(String associationId);

}
