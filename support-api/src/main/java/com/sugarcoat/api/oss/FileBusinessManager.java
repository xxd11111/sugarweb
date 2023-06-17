package com.sugarcoat.api.oss;

import java.util.List;

/**
 * 文件业务绑定服务（对内）
 *
 * @author xxd
 * @date 2023/6/2
 */
public interface FileBusinessManager {

	void associateFile(String associationId, String fileGroup, List<String> fileIds);

	void separateFile(String associationId, String fileGroup);

	void separateAllFile(String associationId);

	List<FileInfo> listAssociationFile(String associationId, String fileGroup);

	List<FileInfo> listAllAssociationFile(String associationId);

}
