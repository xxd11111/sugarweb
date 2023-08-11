package com.sugarcoat.support.oss.client;

import com.sugarcoat.api.oss.FileBusinessManager;
import com.sugarcoat.api.oss.FileInfo;

import java.util.Collection;
import java.util.List;

/**
 * 文件业务绑定客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/31
 */
public class DefaultFileBusinessManager implements FileBusinessManager {

	@Override
	public void associateFile(String associationId, String fileGroup, List<String> fileIds) {

	}

	@Override
	public void separateFile(String associationId, String fileGroup) {

	}

	@Override
	public void separateAllFile(String associationId) {

	}

	@Override
	public void separateFileId(String fileId) {

	}

	@Override
	public void separateFileId(Collection<String> fileIds) {

	}

	@Override
	public List<FileInfo> listAssociationFile(String associationId, String fileGroup) {
		return null;
	}

	@Override
	public List<FileInfo> listAllAssociationFile(String associationId) {
		return null;
	}

}
